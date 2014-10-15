<#list entities as entity>
	<import class="${packagePath}.model.${entity.name}" />
</#list>

<#list entities as entity>
	<#if entity.hasColumns()>
		<class name="${packagePath}.model.impl.${entity.name}Impl" table="${entity.table}"
			<#if entity.isDynamicUpdateEnabled()>
				dynamic-update="true"
			</#if>
		>
			<#if entity.isCacheEnabled()>
				<cache usage="read-write" />
			</#if>

			<#if entity.hasCompoundPK()>
				<composite-id name="primaryKey" class="${packagePath}.service.persistence.${entity.name}PK">
					<#assign pkList = entity.getPKList()>

					<#list pkList as column>
						<key-property name="${column.name}"

						<#if column.name != column.DBName>
							column="${column.DBName}"
						</#if>

						<#if column.isPrimitiveType() || (column.type == "Map") || (column.type == "String")>
							type="com.liferay.portal.dao.orm.hibernate.${serviceBuilder.getPrimitiveObj("${column.type}")}Type"
						</#if>

						<#if column.type == "Date">
							type="org.hibernate.type.TimestampType"
						</#if>

						<#if serviceBuilder.isHBMCamelCasePropertyAccessor(column.name)>
							access="com.liferay.portal.dao.orm.hibernate.CamelCasePropertyAccessor"
						</#if>

						/>
					</#list>
				</composite-id>
			<#else>
				<#assign column = entity.getPKList()?first>

				<id name="${column.name}"
					<#if column.name != column.DBName>
						column="${column.DBName}"
					</#if>

					type="<#if !entity.hasPrimitivePK()>java.lang.</#if>${column.type}"

					<#if serviceBuilder.isHBMCamelCasePropertyAccessor(column.name)>
						access="com.liferay.portal.dao.orm.hibernate.CamelCasePropertyAccessor"
					</#if>

					>

					<#if column.idType??>
						<#assign class = serviceBuilder.getGeneratorClass("${column.idType}")>

						<#if class == "class">
							<#assign class = column.idParam>
						</#if>
					<#else>
						<#assign class = "assigned">
					</#if>

					<generator class="${class}"

					<#if class == "sequence">
							><param name="sequence">${column.idParam}</param>
						</generator>
					<#else>
						/>
					</#if>
				</id>
			</#if>

			<#if entity.isMvccEnabled()>
				<version name="mvccVersion" type="long" access="com.liferay.portal.dao.orm.hibernate.PrivatePropertyAccessor" />
			</#if>

			<#list entity.columnList as column>
				<#if column.EJBName??>
					<#assign ejbName = true>
				<#else>
					<#assign ejbName = false>
				</#if>

				<#if !column.isPrimary() && !column.isCollection() && !ejbName && ((column.type != "Blob") || ((column.type == "Blob") && !column.lazy)) && (column.name != "mvccVersion")>
					<property name="${column.name}"

					<#if serviceBuilder.isHBMCamelCasePropertyAccessor(column.name)>
						access="com.liferay.portal.dao.orm.hibernate.CamelCasePropertyAccessor"
					</#if>

					<#if column.isPrimitiveType() || (column.type == "Map") || (column.type == "String")>
						type="com.liferay.portal.dao.orm.hibernate.${serviceBuilder.getPrimitiveObj("${column.type}")}Type"
					<#else>
						<#if column.type == "Date">
							type="org.hibernate.type.TimestampType"
						<#else>
							type="org.hibernate.type.${column.type}Type"
						</#if>
					</#if>

					<#if column.name != column.DBName>
						column="${column.DBName}"
					</#if>
					/>
				</#if>

				<#if (column.type == "Blob") && column.lazy>
					<one-to-one name="${column.name}BlobModel" access="com.liferay.portal.dao.orm.hibernate.PrivatePropertyAccessor" class="${packagePath}.model.${entity.name}${column.methodName}BlobModel" cascade="save-update" outer-join="false" constrained="true" />
				</#if>
			</#list>
		</class>

		<#list entity.blobList as blobColumn>
			<#if blobColumn.lazy>
				<class name="${packagePath}.model.${entity.name}${blobColumn.methodName}BlobModel" table="${entity.table}" lazy="true"
					<#if entity.isDynamicUpdateEnabled()>
						dynamic-update="true"
					</#if>
				>
					<#assign column = entity.getPKList()?first>

					<id name="${column.name}" column="${column.DBName}">
						<generator class="foreign">
							<param name="property">${packagePath}.model.impl.${entity.name}Impl</param>
						</generator>
					</id>
					<property column="${blobColumn.DBName}" name="${blobColumn.name}Blob" type="blob" />
				</class>
			</#if>
		</#list>
	</#if>
</#list>
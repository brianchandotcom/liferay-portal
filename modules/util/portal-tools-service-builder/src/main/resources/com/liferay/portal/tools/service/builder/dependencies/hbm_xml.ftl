<#list entities as entity>
	<import class="${apiPackagePath}.model.${entity.name}" />
</#list>

<#list entities as entity>
	<#if entity.hasColumns()>
		<class
			<#if entity.isDynamicUpdateEnabled()>
				dynamic-update="true"
			</#if>

			name="${packagePath}.model.impl.${entity.name}Impl" table="${entity.table}"
		>
			<#if entity.hasCompoundPK()>
				<composite-id class="${apiPackagePath}.service.persistence.${entity.name}PK" name="primaryKey">
					<#assign pkList = entity.getPKList()>

					<#list pkList as column>
						<key-property

						<#if serviceBuilder.isHBMCamelCasePropertyAccessor(column.name)>
							access="com.liferay.portal.dao.orm.hibernate.CamelCasePropertyAccessor"
						</#if>

						<#if column.name != column.DBName>
							column="${column.DBName}"
						</#if>

						name="${column.name}"

						<#if column.isPrimitiveType() || (column.type == "Map") || (column.type == "String")>
							type="com.liferay.portal.dao.orm.hibernate.${serviceBuilder.getPrimitiveObj("${column.type}")}Type"
						</#if>

						<#if column.type == "Date">
							type="org.hibernate.type.TimestampType"
						</#if>

						/>
					</#list>
				</composite-id>
			<#else>
				<#assign column = entity.getPKList()?first>

				<id
					<#if serviceBuilder.isHBMCamelCasePropertyAccessor(column.name)>
						access="com.liferay.portal.dao.orm.hibernate.CamelCasePropertyAccessor"
					</#if>

					<#if column.name != column.DBName>
						column="${column.DBName}"
					</#if>

					name="${column.name}"
					type="<#if !entity.hasPrimitivePK()>java.lang.</#if>${column.type}"

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
				<version access="com.liferay.portal.dao.orm.hibernate.PrivatePropertyAccessor" name="mvccVersion" type="long" />
			</#if>

			<#list entity.columnList as column>
				<#if column.EJBName??>
					<#assign ejbName = true>
				<#else>
					<#assign ejbName = false>
				</#if>

				<#if !column.isPrimary() && !column.isCollection() && !ejbName && ((column.type != "Blob") || ((column.type == "Blob") && !column.lazy)) && (column.name != "mvccVersion")>
					<property

					<#if serviceBuilder.isHBMCamelCasePropertyAccessor(column.name)>
						access="com.liferay.portal.dao.orm.hibernate.CamelCasePropertyAccessor"
					</#if>

					<#if column.name != column.DBName>
						column="${column.DBName}"
					</#if>

					name="${column.name}"

					<#if (serviceBuilder.getSqlType(entity.getName(), column.getName(), column.getType()) == "CLOB") && (column.type != "Map")>
						type="com.liferay.portal.dao.orm.hibernate.StringClobType"
					<#elseif column.isPrimitiveType() || (column.type == "Map") || (column.type == "String")>
						type="com.liferay.portal.dao.orm.hibernate.${serviceBuilder.getPrimitiveObj("${column.type}")}Type"
					<#else>
						<#if column.type == "Date">
							type="org.hibernate.type.TimestampType"
						<#else>
							type="org.hibernate.type.${column.type}Type"
						</#if>
					</#if>

					/>
				</#if>

				<#if (column.type == "Blob") && column.lazy>
					<one-to-one access="com.liferay.portal.dao.orm.hibernate.PrivatePropertyAccessor" cascade="save-update" class="${apiPackagePath}.model.${entity.name}${column.methodName}BlobModel" constrained="true" name="${column.name}BlobModel" outer-join="false" />
				</#if>
			</#list>
		</class>

		<#list entity.blobList as blobColumn>
			<#if blobColumn.lazy>
				<class
					<#if entity.isDynamicUpdateEnabled()>
						dynamic-update="true"
					</#if>

					lazy="true" name="${apiPackagePath}.model.${entity.name}${blobColumn.methodName}BlobModel" table="${entity.table}"
				>
					<#assign column = entity.getPKList()?first>

					<id column="${column.DBName}" name="${column.name}">
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
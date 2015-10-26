<#list entities as entity>
	<#assign modelName = packagePath + ".model." + entity.name>

	<#if entity.hasColumns()>
		<model name="${modelName}">
			<#if modelHintsUtil.getDefaultHints(modelName)??>
				<#assign defaultHints = modelHintsUtil.getDefaultHints(modelName)>

				<#if defaultHints?keys?size gt 0>
					<default-hints>
						<#list defaultHints?keys as defaultHintKey>
							<hint name="${defaultHintKey}">${defaultHints[defaultHintKey]}</hint>
						</#list>
					</default-hints>
				</#if>
			</#if>

			<#list entity.columnList as column>
				<#if !column.isCollection()>
					<field name="${column.name}" type="${column.type}"

					<#if column.localized>
						localized="true"
					</#if>

					<#assign closeField = false>

					<#if modelHintsUtil.getFieldsElement(modelName, column.name)??>
						<#assign fieldsElement = modelHintsUtil.getFieldsElement(modelName, column.name)>

						<#assign hintElements = fieldsElement.elements()>

						<#if hintElements?size gt 0>
							<#assign closeField = true>
						</#if>
					</#if>

					<#if modelHintsUtil.getSanitizeTuple(modelName, column.name)??>
						<#assign closeField = true>
					</#if>

					<#if modelHintsUtil.getValidators(modelName, column.name)??>
						<#assign closeField = true>
					</#if>

					<#if closeField>
						>

						<#if modelHintsUtil.getFieldsElement(modelName, column.name)??>
							<#assign fieldsElement = modelHintsUtil.getFieldsElement(modelName, column.name)>

							<#assign hintElements = fieldsElement.elements()>

							<#list hintElements as hintElement>
								<#if hintElement.name == "hint">
									<hint name="${hintElement.attributeValue("name")}">${hintElement.text}</hint>
								<#elseif hintElement.name == "hint-collection">
									<hint-collection name="${hintElement.attributeValue("name")}" />
								</#if>
							</#list>
						</#if>

						<#if modelHintsUtil.getSanitizeTuple(modelName, column.name)??>
							<#assign sanitizeTuple = modelHintsUtil.getSanitizeTuple(modelName, column.name)>

							<#assign contentType = sanitizeTuple.getObject(1)>
							<#assign modes = sanitizeTuple.getObject(2)>

							<sanitize content-type="${contentType}" modes="${modes}" />
						</#if>

					    <#if modelHintsUtil.getValidators(modelName, column.name)??>
							<#assign validators = modelHintsUtil.getValidators(modelName, column.name)>

							<#list validators as validator>
								<#assign validatorName = validator.getObject(1)>
								<#assign validatorErrorMessage = validator.getObject(2)>
								<#assign validatorValue = validator.getObject(3)>

								<validator

								<#if validatorErrorMessage != "">
									error-message="${validatorErrorMessage}"
								</#if>

								name="${validatorName}"

								<#if validatorValue != "">
									>
										${validatorValue}
									</validator>
								<#else>
									/>
								</#if>
							</#list>
						</#if>

						</field>
					<#else>
						/>
					</#if>
				</#if>
			</#list>
		</model>
	</#if>
</#list>
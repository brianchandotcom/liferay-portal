<#list entities as entity>
	<#assign modelName = apiPackagePath + ".model." + entity.name />

	<#if entity.hasEntityColumns()>
		<model name="${modelName}">
			<#if modelHintsUtil.getDefaultHints(modelName)??>
				<#assign defaultHints = modelHintsUtil.getDefaultHints(modelName) />

				<#if defaultHints?keys?size gt 0>
					<default-hints>
						<#list defaultHints?keys as defaultHintKey>
							<hint name="${defaultHintKey}">${defaultHints[defaultHintKey]}</hint>
						</#list>
					</default-hints>
				</#if>
			</#if>

			<#list entity.entityColumns as entityColumn>
				<#if !entityColumn.isCollection()>
					<field

					<#if entityColumn.localized>
						localized="true"
					</#if>

					name="${entityColumn.name}" type="${entityColumn.type}"

					<#assign closeField = false />

					<#if modelHintsUtil.getFieldsElement(modelName, entityColumn.name)??>
						<#assign
							fieldsElement = modelHintsUtil.getFieldsElement(modelName, entityColumn.name)

							hintElements = fieldsElement.elements()
						/>

						<#if hintElements?size gt 0>
							<#assign closeField = true />
						</#if>
					</#if>

					<#if modelHintsUtil.getSanitizeTuple(modelName, entityColumn.name)??>
						<#assign closeField = true />
					</#if>

					<#if modelHintsUtil.getValidators(modelName, entityColumn.name)??>
						<#assign closeField = true />
					</#if>

					<#if closeField>
						>

						<#if modelHintsUtil.getFieldsElement(modelName, entityColumn.name)??>
							<#assign
								fieldsElement = modelHintsUtil.getFieldsElement(modelName, entityColumn.name)

								hintElements = fieldsElement.elements()
							/>

							<#list hintElements as hintElement>
								<#if stringUtil.equals(hintElement.name, "hint")>
									<hint name="${hintElement.attributeValue("name")}">${hintElement.text}</hint>
								<#elseif hintElement.name == "hint-collection">
									<hint-collection name="${hintElement.attributeValue("name")}" />
								</#if>
							</#list>
						</#if>

						<#if modelHintsUtil.getSanitizeTuple(modelName, entityColumn.name)??>
							<#assign
								sanitizeTuple = modelHintsUtil.getSanitizeTuple(modelName, entityColumn.name)

								contentType = sanitizeTuple.getObject(1)
								modes = sanitizeTuple.getObject(2)
							/>

							<sanitize content-type="${contentType}" modes="${modes}" />
						</#if>

						<#if modelHintsUtil.getValidators(modelName, entityColumn.name)??>
							<#assign validators = modelHintsUtil.getValidators(modelName, entityColumn.name) />

							<#list validators as curValidator>
								<#assign
									validatorName = curValidator.getObject(1)
									validatorErrorMessage = curValidator.getObject(2)
									validatorValue = curValidator.getObject(3)
								/>

								<validator

								<#if validator.isNotNull(validatorErrorMessage)>
									error-message="${validatorErrorMessage}"
								</#if>

								name="${validatorName}"

								<#if validator.isNotNull(validatorValue)>
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
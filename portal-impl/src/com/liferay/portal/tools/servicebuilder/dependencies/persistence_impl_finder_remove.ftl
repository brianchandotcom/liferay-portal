<#assign finderColsList = finder.getColumns()>

<#if finder.isCollection()>
	/**
	 * Removes all the ${entity.humanNames} where ${finder.getHumanConditions(false)} from the database.
	 *
	<#list finderColsList as finderCol>
	 * @param ${finderCol.name} the ${finderCol.humanName}
	</#list>
	 * @throws SystemException if a system exception occurred
	 */
	public void removeBy${finder.name}(

	<#list finderColsList as finderCol>
		${finderCol.type} ${finderCol.name}<#if finderCol_has_next>,</#if>
	</#list>

	) throws SystemException {
		for (${entity.name} ${entity.varName} : findBy${finder.name}(

		<#list finderColsList as finderCol>
			${finderCol.name}

			<#if finderCol_has_next>
				,
			</#if>
		</#list>

		)) {
			remove(${entity.varName});
		}
	}
<#else>
	/**
	 * Removes the ${entity.humanName} where ${finder.getHumanConditions(false)} from the database.
	 *
	<#list finderColsList as finderCol>
	 * @param ${finderCol.name} the ${finderCol.humanName}
	</#list>
	 * @return the ${entity.humanName} that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public ${entity.name} removeBy${finder.name}(

	<#list finderColsList as finderCol>
		${finderCol.type} ${finderCol.name}

		<#if finderCol_has_next>
			,
		</#if>
	</#list>

	) throws ${noSuchEntity}Exception, SystemException {
		${entity.name} ${entity.varName} = findBy${finder.name}(

		<#list finderColsList as finderCol>
			${finderCol.name}

			<#if finderCol_has_next>
				,
			</#if>
		</#list>

		);

		return remove(${entity.varName});
	}
</#if>
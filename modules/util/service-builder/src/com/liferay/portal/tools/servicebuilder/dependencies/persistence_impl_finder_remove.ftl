<#assign finderColsList = finder.getColumns()>

<#-- Case 3: finder.isCollection() && !finder.isUnique() -->

<#if finder.isCollection() && !finder.isUnique()>
	/**
	 * Removes all the ${entity.humanNames} where ${finder.getHumanConditions(false)} from the database.
	 *
	<#list finderColsList as finderCol>
	 * @param ${finderCol.name} the ${finderCol.humanName}
	</#list>
	 */
	@Override
	public void removeBy${finder.name}(

	<#list finderColsList as finderCol>
		${finderCol.type} ${finderCol.name}<#if finderCol_has_next>,</#if>
	</#list>

	) {
		for (${entity.name} ${entity.varName} : findBy${finder.name}(

		<#list finderColsList as finderCol>
			${finderCol.name},
		</#list>

		QueryUtil.ALL_POS, QueryUtil.ALL_POS, null
		)) {
			remove(${entity.varName});
		}
	}
<#else>

<#-- Case 9: !finder.isCollection() || finder.isUnique() -->

	/**
	 * Removes the ${entity.humanName} where ${finder.getHumanConditions(false)} from the database.
	 *
	<#list finderColsList as finderCol>
	 * @param ${finderCol.name} the ${finderCol.humanName}
	</#list>
	 * @return the ${entity.humanName} that was removed
	 */
	@Override
	public ${entity.name} removeBy${finder.name}(

	<#list finderColsList as finderCol>
		${finderCol.type} ${finderCol.name}

		<#if finderCol_has_next>
			,
		</#if>
	</#list>

	) throws ${noSuchEntity}Exception {
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
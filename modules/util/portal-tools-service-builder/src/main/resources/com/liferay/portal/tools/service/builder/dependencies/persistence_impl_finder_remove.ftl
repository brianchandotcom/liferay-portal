<#assign entityColumns = entityFinder.entityColumns />

<#-- Case 3: entityFinder.isCollection() && !entityFinder.isUnique() -->

<#if entityFinder.isCollection() && !entityFinder.isUnique()>
	/**
	 * Removes all the ${entity.pluralHumanName} where ${entityFinder.getHumanConditions(false)} from the database.
	 *
	<#list entityColumns as entityColumn>
	 * @param ${entityColumn.name} the ${entityColumn.humanName}
	</#list>
	 */
	@Override
	public void removeBy${entityFinder.name}(

	<#list entityColumns as entityColumn>
		${entityColumn.type} ${entityColumn.name}<#if entityColumn_has_next>,</#if>
	</#list>

	) {
		for (${entity.name} ${entity.varName} : findBy${entityFinder.name}(

		<#list entityColumns as entityColumn>
			${entityColumn.name},
		</#list>

		QueryUtil.ALL_POS, QueryUtil.ALL_POS, null
		)) {
			remove(${entity.varName});
		}
	}
<#else>

<#-- Case 9: !entityFinder.isCollection() || entityFinder.isUnique() -->

	/**
	 * Removes the ${entity.humanName} where ${entityFinder.getHumanConditions(false)} from the database.
	 *
	<#list entityColumns as entityColumn>
	 * @param ${entityColumn.name} the ${entityColumn.humanName}
	</#list>
	 * @return the ${entity.humanName} that was removed
	 */
	@Override
	public ${entity.name} removeBy${entityFinder.name}(

	<#list entityColumns as entityColumn>
		${entityColumn.type} ${entityColumn.name}

		<#if entityColumn_has_next>
			,
		</#if>
	</#list>

	) throws ${noSuchEntity}Exception {
		${entity.name} ${entity.varName} = findBy${entityFinder.name}(

		<#list entityColumns as entityColumn>
			${entityColumn.name}

			<#if entityColumn_has_next>
				,
			</#if>
		</#list>

		);

		return remove(${entity.varName});
	}
</#if>
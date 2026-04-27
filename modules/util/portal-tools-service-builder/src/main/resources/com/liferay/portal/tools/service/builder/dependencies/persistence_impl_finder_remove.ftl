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
		<#if entityFinder.collectionPersistenceFinderEnabled>
			_collectionPersistenceFinderBy${entityFinder.name}.remove(
				${finderCacheInstance},
				new Object[] {
					<#list entityColumns as entityColumn>
						${entityColumn.name}

						<#if entityColumn_has_next>
							,
						</#if>
					</#list>
				});
		<#else>
			for (${entity.name} ${entity.variableName} : findBy${entityFinder.name}(

			<#list entityColumns as entityColumn>
				${entityColumn.name},
			</#list>

			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null
			)) {
				remove(${entity.variableName});
			}
		</#if>
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
		${entity.name} ${entity.variableName} = findBy${entityFinder.name}(

		<#list entityColumns as entityColumn>
			${entityColumn.name}

			<#if entityColumn_has_next>
				,
			</#if>
		</#list>

		);

		return remove(${entity.variableName});
	}
</#if>
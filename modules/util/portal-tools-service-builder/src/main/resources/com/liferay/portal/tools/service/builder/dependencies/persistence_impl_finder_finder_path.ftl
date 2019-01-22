<#assign entityColumns = entityFinder.entityColumns />

<#if entityFinder.isCollection()>
	private final FinderPath _finderPathWithPaginationFindBy${entityFinder.name} = new FinderPath(
		${entity.name}ModelImpl.ENTITY_CACHE_ENABLED,
		${entity.name}ModelImpl.FINDER_CACHE_ENABLED,
		${entity.name}Impl.class,
		FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
		"findBy${entityFinder.name}",
		new String[] {
			<#list entityColumns as entityColumn>
				${serviceBuilder.getPrimitiveObj("${entityColumn.type}")}.class.getName(),
			</#list>

			Integer.class.getName(), Integer.class.getName(), OrderByComparator.class.getName()
		});

	<#if !entityFinder.hasCustomComparator()>
		private final FinderPath _finderPathWithoutPaginationFindBy${entityFinder.name} = new FinderPath(
			${entity.name}ModelImpl.ENTITY_CACHE_ENABLED,
			${entity.name}ModelImpl.FINDER_CACHE_ENABLED,
			${entity.name}Impl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findBy${entityFinder.name}",
			new String[] {
				<#list entityColumns as entityColumn>
					${serviceBuilder.getPrimitiveObj("${entityColumn.type}")}.class.getName()

					<#if entityColumn_has_next>
						,
					</#if>
				</#list>
			}

			<#if columnBitmaskEnabled>
				,

				<#list entityColumns as entityColumn>
					${entity.name}ModelImpl.${entityColumn.name?upper_case}_COLUMN_BITMASK

					<#if entityColumn_has_next>
						|
					</#if>
				</#list>

				<#if entity.entityOrder??>
					<#list entity.entityOrder.entityColumns as entityColumn>
						<#if !entityColumns?seq_contains(entityColumn) && !entity.PKEntityColumns?seq_contains(entityColumn)>
							| ${entity.name}ModelImpl.${entityColumn.name?upper_case}_COLUMN_BITMASK
						</#if>
					</#list>
				</#if>
			</#if>

			);
	</#if>
</#if>

<#if !entityFinder.isCollection() || entityFinder.isUnique()>
	private final FinderPath _finderPathFetchBy${entityFinder.name} = new FinderPath(
		${entity.name}ModelImpl.ENTITY_CACHE_ENABLED,
		${entity.name}ModelImpl.FINDER_CACHE_ENABLED,
		${entity.name}Impl.class,
		FINDER_CLASS_NAME_ENTITY,
		"fetchBy${entityFinder.name}",
		new String[] {
			<#list entityColumns as entityColumn>
				${serviceBuilder.getPrimitiveObj("${entityColumn.type}")}.class.getName()

				<#if entityColumn_has_next>
					,
				</#if>
			</#list>
		}

		<#if columnBitmaskEnabled>
			,

			<#list entityColumns as entityColumn>
				${entity.name}ModelImpl.${entityColumn.name?upper_case}_COLUMN_BITMASK

				<#if entityColumn_has_next>
					|
				</#if>
			</#list>
		</#if>

		);
</#if>

<#if !entityFinder.hasCustomComparator()>
	private final FinderPath _finderPathCountBy${entityFinder.name} = new FinderPath(
		${entity.name}ModelImpl.ENTITY_CACHE_ENABLED,
		${entity.name}ModelImpl.FINDER_CACHE_ENABLED,
		Long.class,
		FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
		"countBy${entityFinder.name}",
		new String[] {
			<#list entityColumns as entityColumn>
				${serviceBuilder.getPrimitiveObj("${entityColumn.type}")}.class.getName()

				<#if entityColumn_has_next>
					,
				</#if>
			</#list>
		});
</#if>

<#if entityFinder.hasArrayableOperator() || entityFinder.hasCustomComparator()>
	private final FinderPath _finderPathWithPaginationCountBy${entityFinder.name} = new FinderPath(
		${entity.name}ModelImpl.ENTITY_CACHE_ENABLED,
		${entity.name}ModelImpl.FINDER_CACHE_ENABLED,
		Long.class,
		FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
		"countBy${entityFinder.name}",
		new String[] {
			<#list entityColumns as entityColumn>
				${serviceBuilder.getPrimitiveObj("${entityColumn.type}")}.class.getName()

				<#if entityColumn_has_next>
					,
				</#if>
			</#list>
		});
</#if>
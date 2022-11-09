<#assign entityColumns = entityFinder.entityColumns />

<#if entityFinder.isCollection()>
	private FinderPath _finderPathWithPaginationFindBy${entityFinder.name};

	<#if !entityFinder.hasCustomComparator()>
		private FinderPath _finderPathWithoutPaginationFindBy${entityFinder.name};

		<#if serviceBuilder.isVersionGTE_7_4_0()>
			@Override
			public FinderPath getFinderPathWithoutPaginationFindBy${entityFinder.name}() {
				return _finderPathWithoutPaginationFindBy${entityFinder.name};
			}
		</#if>
	</#if>
</#if>

<#if !entityFinder.isCollection() || entityFinder.isUnique()>
	private FinderPath _finderPathFetchBy${entityFinder.name};

	<#if serviceBuilder.isVersionGTE_7_4_0()>
		@Override
		public FinderPath getFinderPathFetchBy${entityFinder.name}() {
			return _finderPathFetchBy${entityFinder.name};
		}
	</#if>
</#if>

<#if !entityFinder.hasCustomComparator()>
	private FinderPath _finderPathCountBy${entityFinder.name};

	<#if serviceBuilder.isVersionGTE_7_4_0()>
		@Override
		public FinderPath getFinderPathCountBy${entityFinder.name}() {
			return _finderPathCountBy${entityFinder.name};
		}
	</#if>
</#if>

<#if entityFinder.hasArrayableOperator() || entityFinder.hasCustomComparator()>
	private FinderPath _finderPathWithPaginationCountBy${entityFinder.name};
</#if>
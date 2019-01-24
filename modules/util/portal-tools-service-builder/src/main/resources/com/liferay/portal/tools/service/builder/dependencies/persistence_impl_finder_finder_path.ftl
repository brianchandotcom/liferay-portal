<#assign entityColumns = entityFinder.entityColumns />

<#if entityFinder.isCollection()>
	private FinderPath _finderPathWithPaginationFindBy${entityFinder.name};

	<#if !entityFinder.hasCustomComparator()>
		private FinderPath _finderPathWithoutPaginationFindBy${entityFinder.name};
	</#if>
</#if>

<#if !entityFinder.isCollection() || entityFinder.isUnique()>
	private FinderPath _finderPathFetchBy${entityFinder.name};
</#if>

<#if !entityFinder.hasCustomComparator()>
	private FinderPath _finderPathCountBy${entityFinder.name};
</#if>

<#if entityFinder.hasArrayableOperator() || entityFinder.hasCustomComparator()>
	private FinderPath _finderPathWithPaginationCountBy${entityFinder.name};
</#if>
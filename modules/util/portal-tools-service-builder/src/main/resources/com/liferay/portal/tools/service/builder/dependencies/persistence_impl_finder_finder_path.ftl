<#assign entityColumns = entityFinder.entityColumns />

<#if !entityFinder.collectionPersistenceFinderEnabled>
	<#if entityFinder.isCollection()>
		private FinderPath _finderPathWithPaginationFindBy${entityFinder.name};

		<#if !entityFinder.hasCustomComparator()>
			private FinderPath _finderPathWithoutPaginationFindBy${entityFinder.name};
		</#if>
	</#if>

	<#if (!entityFinder.isCollection() || entityFinder.isUnique()) && !entityFinder.uniquePersistenceFinderEnabled>
		private FinderPath _finderPathFetchBy${entityFinder.name};
	</#if>

	<#if !entityFinder.hasCustomComparator() && (entityFinder.isCollection() || serviceBuilder.isVersionLTE_7_3_0())>
		private FinderPath _finderPathCountBy${entityFinder.name};
	</#if>

	<#if entityFinder.hasCustomComparator() || entityFinder.hasArrayableOperator()>
		private FinderPath _finderPathWithPaginationCountBy${entityFinder.name};
	</#if>
</#if>

<#if entityFinder.collectionPersistenceFinderEnabled>
	<#if entity.isPermissionCheckEnabled(entityFinder)>
		private FilterCollectionPersistenceFinder<${entity.name}, ${noSuchEntity}Exception> _collectionPersistenceFinderBy${entityFinder.name};
	<#else>
		private CollectionPersistenceFinder<${entity.name}, ${noSuchEntity}Exception> _collectionPersistenceFinderBy${entityFinder.name};
	</#if>
</#if>

<#if entityFinder.uniquePersistenceFinderEnabled>
	private UniquePersistenceFinder<${entity.name}, ${noSuchEntity}Exception> _uniquePersistenceFinderBy${entityFinder.name};
</#if>
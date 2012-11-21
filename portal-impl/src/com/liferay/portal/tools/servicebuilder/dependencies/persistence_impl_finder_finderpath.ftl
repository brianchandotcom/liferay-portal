<#assign finderColsList = finder.getColumns()>

<#if finder.isCollection()>
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_${finder.name?upper_case} = new FinderPath(
		${entity.name}ModelImpl.ENTITY_CACHE_ENABLED,
		${entity.name}ModelImpl.FINDER_CACHE_ENABLED,
		${entity.name}Impl.class,
		FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
		"findBy${finder.name}",
		new String[] {
			<#list finderColsList as finderCol>
				${serviceBuilder.getPrimitiveObj("${finderCol.type}")}.class.getName(),
			</#list>

			"java.lang.Integer", "java.lang.Integer", "com.liferay.portal.kernel.util.OrderByComparator"
		});

	<#if !finder.hasCustomComparator()>
		public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_${finder.name?upper_case} = new FinderPath(
			${entity.name}ModelImpl.ENTITY_CACHE_ENABLED,
			${entity.name}ModelImpl.FINDER_CACHE_ENABLED,
			${entity.name}Impl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findBy${finder.name}",
			new String[] {
				<#list finderColsList as finderCol>
					${serviceBuilder.getPrimitiveObj("${finderCol.type}")}.class.getName()

					<#if finderCol_has_next>
						,
					</#if>
				</#list>
			}

			<#if columnBitmaskEnabled>
				,

				<#list finderColsList as finderCol>
					${entity.name}ModelImpl.${finderCol.name?upper_case}_COLUMN_BITMASK

					<#if finderCol_has_next>
						|
					</#if>
				</#list>
			</#if>

			);
	</#if>
<#else>
	public static final FinderPath FINDER_PATH_FETCH_BY_${finder.name?upper_case} = new FinderPath(
		${entity.name}ModelImpl.ENTITY_CACHE_ENABLED,
		${entity.name}ModelImpl.FINDER_CACHE_ENABLED,
		${entity.name}Impl.class,
		FINDER_CLASS_NAME_ENTITY,
		"fetchBy${finder.name}",
		new String[] {
			<#list finderColsList as finderCol>
				${serviceBuilder.getPrimitiveObj("${finderCol.type}")}.class.getName()

				<#if finderCol_has_next>
					,
				</#if>
			</#list>
		}

		<#if columnBitmaskEnabled>
			,

			<#list finderColsList as finderCol>
				${entity.name}ModelImpl.${finderCol.name?upper_case}_COLUMN_BITMASK

				<#if finderCol_has_next>
					|
				</#if>
			</#list>
		</#if>

		);
</#if>

<#if !finder.hasCustomComparator()>
	public static final FinderPath FINDER_PATH_COUNT_BY_${finder.name?upper_case} = new FinderPath(
		${entity.name}ModelImpl.ENTITY_CACHE_ENABLED,
		${entity.name}ModelImpl.FINDER_CACHE_ENABLED,
		Long.class,
		FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
		"countBy${finder.name}",
		new String[] {
			<#list finderColsList as finderCol>
				${serviceBuilder.getPrimitiveObj("${finderCol.type}")}.class.getName()

				<#if finderCol_has_next>
					,
				</#if>
			</#list>
		});
</#if>

<#if finder.hasArrayableOperator() || finder.hasCustomComparator()>
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_${finder.name?upper_case} = new FinderPath(
		${entity.name}ModelImpl.ENTITY_CACHE_ENABLED,
		${entity.name}ModelImpl.FINDER_CACHE_ENABLED,
		Long.class,
		FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
		"countBy${finder.name}",
		new String[] {
			<#list finderColsList as finderCol>
				${serviceBuilder.getPrimitiveObj("${finderCol.type}")}.class.getName()

				<#if finderCol_has_next>
					,
				</#if>
			</#list>
		});
</#if>
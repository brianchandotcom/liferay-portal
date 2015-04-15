StringBundler query = new StringBundler();

query.append(_SQL_SELECT_${entity.alias?upper_case}_WHERE);

<#include "persistence_impl_finder_arrayable_cols.ftl">

if (orderByComparator != null) {
	appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
}
else

<#if checkPagination>
	if (pagination)
</#if>

{
	query.append(${entity.name}ModelImpl.ORDER_BY_JPQL);
}
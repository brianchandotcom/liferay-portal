StringBundler sb = null;

if (orderByComparator != null) {
	sb = new StringBundler(${entityColumns?size + 2} + (orderByComparator.getOrderByFields().length * 2));
}
else {
	sb = new StringBundler(${entityColumns?size + 2});
}

sb.append(_SQL_SELECT_${entity.alias?upper_case}_WHERE);

<#include "persistence_impl_finder_cols.ftl">

if (orderByComparator != null) {
	appendOrderByComparator(sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
}
else {
	sb.append(${entity.name}ModelImpl.ORDER_BY_JPQL);
}
StringBundler sb = null;

if (orderByComparator != null) {
	sb = new StringBundler(${entityColumns?size + 3} + (orderByComparator.getOrderByConditionFields().length * 3) + (orderByComparator.getOrderByFields().length * 3));
}
else {
	sb = new StringBundler(${entityColumns?size + 2});
}

sb.append(_SQL_SELECT_${entity.alias?upper_case}_WHERE);

<#include "persistence_impl_finder_cols.ftl">

if (orderByComparator != null) {
	String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

	if (orderByConditionFields.length > 0) {
		sb.append(WHERE_AND);
	}

	for (int i = 0; i < orderByConditionFields.length; i++) {
		sb.append(_ORDER_BY_ENTITY_ALIAS);
		sb.append(orderByConditionFields[i]);

		if ((i + 1) < orderByConditionFields.length) {
			if (orderByComparator.isAscending() ^ previous) {
				sb.append(WHERE_GREATER_THAN_HAS_NEXT);
			}
			else {
				sb.append(WHERE_LESSER_THAN_HAS_NEXT);
			}
		}
		else {
			if (orderByComparator.isAscending() ^ previous) {
				sb.append(WHERE_GREATER_THAN);
			}
			else {
				sb.append(WHERE_LESSER_THAN);
			}
		}
	}

	sb.append(ORDER_BY_CLAUSE);

	String[] orderByFields = orderByComparator.getOrderByFields();

	for (int i = 0; i < orderByFields.length; i++) {
		sb.append(_ORDER_BY_ENTITY_ALIAS);
		sb.append(orderByFields[i]);

		if ((i + 1) < orderByFields.length) {
			if (orderByComparator.isAscending() ^ previous) {
				sb.append(ORDER_BY_ASC_HAS_NEXT);
			}
			else {
				sb.append(ORDER_BY_DESC_HAS_NEXT);
			}
		}
		else {
			if (orderByComparator.isAscending() ^ previous) {
				sb.append(ORDER_BY_ASC);
			}
			else {
				sb.append(ORDER_BY_DESC);
			}
		}
	}
}
else {
	sb.append(${entity.name}ModelImpl.ORDER_BY_JPQL);
}
StringBundler query = null;

if (orderByComparator != null) {
	query = new StringBundler(6 + (orderByComparator.getOrderByFields().length * 6));
}
else {
	query = new StringBundler(3);
}

query.append(_SQL_SELECT_${entity.alias?upper_case}_WHERE);

<#include "persistence_impl_finder_cols.ftl">

if (orderByComparator != null) {
	String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

	if (orderByConditionFields.length > 0) {
		query.append(WHERE_AND);
	}

	for (int i = 0; i < orderByConditionFields.length; i++) {
		query.append(_ORDER_BY_ENTITY_ALIAS);
		query.append(orderByConditionFields[i]);

		if ((i + 1) < orderByConditionFields.length) {
			if (orderByComparator.isAscending() ^ previous) {
				query.append(WHERE_GREATER_THAN_HAS_NEXT);
			}
			else {
				query.append(WHERE_LESSER_THAN_HAS_NEXT);
			}
		}
		else {
			if (orderByComparator.isAscending() ^ previous) {
				query.append(WHERE_GREATER_THAN);
			}
			else {
				query.append(WHERE_LESSER_THAN);
			}
		}
	}

	query.append(ORDER_BY_CLAUSE);

	String[] orderByFields = orderByComparator.getOrderByFields();

	for (int i = 0; i < orderByFields.length; i++) {
		query.append(_ORDER_BY_ENTITY_ALIAS);
		query.append(orderByFields[i]);

		if ((i + 1) < orderByFields.length) {
			if (orderByComparator.isAscending() ^ previous) {
				query.append(ORDER_BY_ASC_HAS_NEXT);
			}
			else {
				query.append(ORDER_BY_DESC_HAS_NEXT);
			}
		}
		else {
			if (orderByComparator.isAscending() ^ previous) {
				query.append(ORDER_BY_ASC);
			}
			else {
				query.append(ORDER_BY_DESC);
			}
		}
	}
}
else {
	query.append(${entity.name}ModelImpl.ORDER_BY_JPQL);
}
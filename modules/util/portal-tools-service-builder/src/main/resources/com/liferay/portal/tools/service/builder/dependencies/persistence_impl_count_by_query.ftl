StringBundler sb = new StringBundler(${entityColumns?size + 1});

sb.append(_SQL_COUNT_${entity.alias?upper_case}_WHERE);

<#include "persistence_impl_finder_cols.ftl">
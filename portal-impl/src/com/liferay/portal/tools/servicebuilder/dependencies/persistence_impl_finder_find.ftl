<#assign finderColsList = finder.getColumns()>

<#--
Basic Cases Table:

+---------------------------+-------------------------------+-------------------------------+
|							|	finder.isCollection()		|	!finder.isCollection()		|
+---------------------------+-------------------------------+-------------------------------+
|	finder.isUnique()		|			Case 1				|			Case 2				|
+---------------------------+-------------------------------+-------------------------------+
|	!finder.isUnique()		|			Case 3				|			Case 4				|
+---------------------------+-------------------------------+-------------------------------+

Combination Cases Table 1:

+---------------------------+-------------------------------+-------------------------------+
|							|	finder.isCollection()		|	!finder.isCollection()		|
+---------------------------+---------------------------------------------------------------+
|	finder.isUnique()		|							Case 5								|
+---------------------------+---------------------------------------------------------------+
|	!finder.isUnique()		|							Case 6								|
+---------------------------+---------------------------------------------------------------+

Combination Cases Table 2:

+---------------------------+-------------------------------+-------------------------------+
|							|	finder.isCollection()		|	!finder.isCollection()		|
+---------------------------+-------------------------------+-------------------------------+
|	finder.isUnique()		|								|								|
+---------------------------|			Case 7				|			Case 8				|
|	!finder.isUnique()		|								|								|
+---------------------------+-------------------------------+-------------------------------+

Combination Cases Table 3:

+---------------------------+-------------------------------+-------------------------------+
|							|	finder.isCollection()		|	!finder.isCollection()		|
+---------------------------+-------------------------------+-------------------------------+
|	finder.isUnique()		|																|
+---------------------------|--------------------------------			Case 9				|
|	!finder.isUnique()		|								|								|
+---------------------------+-------------------------------+-------------------------------+

There are a total of 9 cases. The first 4 cases are the basic cases as show in
the first table.

The additional combination case tables allow us to group the basic cases. For
example:

A combination of case 1 and case 2 is grouped as case 5.

A combination of case 3 and case 4 is grouped as case 6.

A combination of case 1 and case 3 is grouped as case 7.

A combination of case 2 and case 4 is grouped as case 8.

A combination of case 1, case 2, and case 4 is grouped as case 9.

Grouping the basic cases allows us to write the finder implementation with as
little duplicate code as possible.

finder.isUnique() means a literal unique finder because it generates a unique
index at the database level. !finder.isCollection() means a conceptual unique
that may or may not be enforced with a unique index at the database level. Case
9 can be considered a union of the literal and conceptual unique finders.
-->

<#-- Case 1: finder.isCollection() && finder.isUnique() -->

<#if finder.isCollection() && finder.isUnique()>
</#if>

<#-- Case 2: !finder.isCollection() && finder.isUnique() -->

<#if !finder.isCollection() && finder.isUnique()>
</#if>

<#-- Case 3: finder.isCollection() && !finder.isUnique() -->

<#if finder.isCollection() && !finder.isUnique()>
	/**
	 * Returns all the ${entity.humanNames} where ${finder.getHumanConditions(false)}.
	 *
	<#list finderColsList as finderCol>
	 * @param ${finderCol.name} the ${finderCol.humanName}
	</#list>
	 * @return the matching ${entity.humanNames}
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<${entity.name}> findBy${finder.name}(

	<#list finderColsList as finderCol>
		${finderCol.type} ${finderCol.name}

		<#if finderCol_has_next>
			,
		</#if>
	</#list>

	) throws SystemException {
		return findBy${finder.name}(

		<#list finderColsList as finderCol>
			${finderCol.name},
		</#list>

		QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ${entity.humanNames} where ${finder.getHumanConditions(false)}.
	 *
	 * <p>
	 * <#include "range_comment.ftl">
	 * </p>
	 *
	<#list finderColsList as finderCol>
	 * @param ${finderCol.name} the ${finderCol.humanName}
	</#list>
	 * @param start the lower bound of the range of ${entity.humanNames}
	 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
	 * @return the range of matching ${entity.humanNames}
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<${entity.name}> findBy${finder.name}(

	<#list finderColsList as finderCol>
		${finderCol.type} ${finderCol.name},
	</#list>

	int start, int end) throws SystemException {
		return findBy${finder.name}(

		<#list finderColsList as finderCol>
			${finderCol.name},
		</#list>

		start, end, null);
	}

	/**
	 * Returns an ordered range of all the ${entity.humanNames} where ${finder.getHumanConditions(false)}.
	 *
	 * <p>
	 * <#include "range_comment.ftl">
	 * </p>
	 *
	<#list finderColsList as finderCol>
	 * @param ${finderCol.name} the ${finderCol.humanName}
	</#list>
	 * @param start the lower bound of the range of ${entity.humanNames}
	 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ${entity.humanNames}
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<${entity.name}> findBy${finder.name}(

	<#list finderColsList as finderCol>
		${finderCol.type} ${finderCol.name},
	</#list>

	int start, int end, OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		<#if !finder.hasCustomComparator()>
			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) && (orderByComparator == null)) {
				pagination = false;
				finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_${finder.name?upper_case};
				finderArgs = new Object[] {
					<#list finderColsList as finderCol>
						${finderCol.name}

						<#if finderCol_has_next>
							,
						</#if>
					</#list>
				};
			}
			else {
		</#if>

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_${finder.name?upper_case};
		finderArgs = new Object[] {
			<#list finderColsList as finderCol>
				${finderCol.name},
			</#list>

			start, end, orderByComparator
		};

		<#if !finder.hasCustomComparator()>
			}
		</#if>

		List<${entity.name}> list = (List<${entity.name}>)FinderCacheUtil.getResult(finderPath, finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (${entity.name} ${entity.varName} : list) {
				if (
					<#list finderColsList as finderCol>
						<#if finderCol.comparator == "=">
							<#if finderCol.isPrimitiveType(false)>
								(${finderCol.name} != ${entity.varName}.get${finderCol.methodName}())
							<#else>
								!Validator.equals(${finderCol.name}, ${entity.varName}.get${finderCol.methodName}())
							</#if>
						<#elseif finderCol.comparator == "!=">
							<#if finderCol.isPrimitiveType(false)>
								(${finderCol.name} == ${entity.varName}.get${finderCol.methodName}())
							<#else>
								Validator.equals(${finderCol.name}, ${entity.varName}.get${finderCol.methodName}())
							</#if>
						<#elseif finderCol.comparator == ">">
							<#if finderCol.type == "Date">
								(${finderCol.name}.getTime() >= ${entity.varName}.get${finderCol.methodName}().getTime())
							<#else>
								(${finderCol.name} >= ${entity.varName}.get${finderCol.methodName}())
							</#if>
						<#elseif finderCol.comparator == ">=">
							<#if finderCol.type == "Date">
								(${finderCol.name}.getTime() > ${entity.varName}.get${finderCol.methodName}().getTime())
							<#else>
								(${finderCol.name} > ${entity.varName}.get${finderCol.methodName}())
							</#if>
						<#elseif finderCol.comparator == "<">
							<#if finderCol.type == "Date">
								(${finderCol.name}.getTime() <= ${entity.varName}.get${finderCol.methodName}().getTime())
							<#else>
								(${finderCol.name} <= ${entity.varName}.get${finderCol.methodName}())
							</#if>
						<#elseif finderCol.comparator == "<=">
							<#if finderCol.type == "Date">
								(${finderCol.name}.getTime() < ${entity.varName}.get${finderCol.methodName}().getTime())
							<#else>
								(${finderCol.name} < ${entity.varName}.get${finderCol.methodName}())
							</#if>
						<#elseif finderCol.comparator == "LIKE">
							!StringUtil.wildcardMatches(${entity.varName}.get${finderCol.methodName}(), ${finderCol.name}, CharPool.UNDERLINE, CharPool.PERCENT, CharPool.BACK_SLASH,
							<#if finderCol.isCaseSensitive()>
								true
							<#else>
								false
							</#if>
							)
						</#if>
						<#if finderCol_has_next>
							||
						</#if>
					</#list>
				) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			<#assign checkPagination = true>

			<#include "persistence_impl_find_by_query.ftl">

			<#assign checkPagination = false>

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				<#include "persistence_impl_finder_qpos.ftl">

				if (!pagination) {
					list = (List<${entity.name}>)QueryUtil.list(q, getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<${entity.name}>(list);
				}
				else {
					list = (List<${entity.name}>)QueryUtil.list(q, getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first ${entity.humanName} in the ordered set where ${finder.getHumanConditions(false)}.
	 *
	<#list finderColsList as finderCol>
	 * @param ${finderCol.name} the ${finderCol.humanName}
	</#list>
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ${entity.humanName}
	 * @throws ${packagePath}.${noSuchEntity}Exception if a matching ${entity.humanName} could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ${entity.name} findBy${finder.name}_First(

	<#list finderColsList as finderCol>
		${finderCol.type} ${finderCol.name},
	</#list>

	OrderByComparator orderByComparator) throws ${noSuchEntity}Exception, SystemException {
		${entity.name} ${entity.varName} = fetchBy${finder.name}_First(

		<#list finderColsList as finderCol>
			${finderCol.name},
		</#list>

		orderByComparator);

		if (${entity.varName} != null) {
			return ${entity.varName};
		}

		StringBundler msg = new StringBundler(${(finderColsList?size * 2) + 2});

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		<#list finderColsList as finderCol>
			msg.append("<#if finderCol_index != 0>, </#if>${finderCol.name}=");
			msg.append(${finderCol.name});

			<#if !finderCol_has_next>
				msg.append(StringPool.CLOSE_CURLY_BRACE);
			</#if>
		</#list>

		throw new ${noSuchEntity}Exception(msg.toString());
	}

	/**
	 * Returns the first ${entity.humanName} in the ordered set where ${finder.getHumanConditions(false)}.
	 *
	<#list finderColsList as finderCol>
	 * @param ${finderCol.name} the ${finderCol.humanName}
	</#list>
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ${entity.humanName}, or <code>null</code> if a matching ${entity.humanName} could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ${entity.name} fetchBy${finder.name}_First(

	<#list finderColsList as finderCol>
		${finderCol.type} ${finderCol.name},
	</#list>

	OrderByComparator orderByComparator) throws SystemException {
		List<${entity.name}> list = findBy${finder.name}(

		<#list finderColsList as finderCol>
			${finderCol.name},
		</#list>

		0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ${entity.humanName} in the ordered set where ${finder.getHumanConditions(false)}.
	 *
	<#list finderColsList as finderCol>
	 * @param ${finderCol.name} the ${finderCol.humanName}
	</#list>
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ${entity.humanName}
	 * @throws ${packagePath}.${noSuchEntity}Exception if a matching ${entity.humanName} could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ${entity.name} findBy${finder.name}_Last(

	<#list finderColsList as finderCol>
		${finderCol.type} ${finderCol.name},
	</#list>

	OrderByComparator orderByComparator) throws ${noSuchEntity}Exception, SystemException {
		${entity.name} ${entity.varName} = fetchBy${finder.name}_Last(

		<#list finderColsList as finderCol>
			${finderCol.name},
		</#list>

		orderByComparator);

		if (${entity.varName} != null) {
			return ${entity.varName};
		}

		StringBundler msg = new StringBundler(${(finderColsList?size * 2) + 2});

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		<#list finderColsList as finderCol>
			msg.append("<#if finderCol_index != 0>, </#if>${finderCol.name}=");
			msg.append(${finderCol.name});

			<#if !finderCol_has_next>
				msg.append(StringPool.CLOSE_CURLY_BRACE);
			</#if>
		</#list>

		throw new ${noSuchEntity}Exception(msg.toString());
	}

	/**
	 * Returns the last ${entity.humanName} in the ordered set where ${finder.getHumanConditions(false)}.
	 *
	<#list finderColsList as finderCol>
	 * @param ${finderCol.name} the ${finderCol.humanName}
	</#list>
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ${entity.humanName}, or <code>null</code> if a matching ${entity.humanName} could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ${entity.name} fetchBy${finder.name}_Last(

	<#list finderColsList as finderCol>
		${finderCol.type} ${finderCol.name},
	</#list>

	OrderByComparator orderByComparator) throws SystemException {
		int count = countBy${finder.name}(

		<#list finderColsList as finderCol>
			${finderCol.name}

			<#if finderCol_has_next>
				,
			</#if>
		</#list>

		);

		if (count == 0) {
			return null;
		}

		List<${entity.name}> list = findBy${finder.name}(

		<#list finderColsList as finderCol>
			${finderCol.name},
		</#list>

		count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	<#if !finder.hasColumn(entity.PKVarName)>
		/**
		 * Returns the ${entity.humanNames} before and after the current ${entity.humanName} in the ordered set where ${finder.getHumanConditions(false)}.
		 *
		 * @param ${entity.PKVarName} the primary key of the current ${entity.humanName}
		<#list finderColsList as finderCol>
		 * @param ${finderCol.name} the ${finderCol.humanName}
		</#list>
		 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
		 * @return the previous, current, and next ${entity.humanName}
		 * @throws ${packagePath}.${noSuchEntity}Exception if a ${entity.humanName} with the primary key could not be found
		 * @throws SystemException if a system exception occurred
		 */
		@Override
		public ${entity.name}[] findBy${finder.name}_PrevAndNext(${entity.PKClassName} ${entity.PKVarName},

		<#list finderColsList as finderCol>
			${finderCol.type} ${finderCol.name},
		</#list>

		OrderByComparator orderByComparator) throws ${noSuchEntity}Exception, SystemException {
			${entity.name} ${entity.varName} = findByPrimaryKey(${entity.PKVarName});

			Session session = null;

			try {
				session = openSession();

				${entity.name}[] array = new ${entity.name}Impl[3];

				array[0] =
					getBy${finder.name}_PrevAndNext(
						session, ${entity.varName},

						<#list finderColsList as finderCol>
							${finderCol.name},
						</#list>

						orderByComparator, true);

				array[1] = ${entity.varName};

				array[2] =
					getBy${finder.name}_PrevAndNext(
						session, ${entity.varName},

						<#list finderColsList as finderCol>
							${finderCol.name},
						</#list>

						orderByComparator, false);

				return array;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		protected ${entity.name} getBy${finder.name}_PrevAndNext(
			Session session, ${entity.name} ${entity.varName},

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name},
			</#list>

			OrderByComparator orderByComparator, boolean previous) {

			<#include "persistence_impl_get_by_prev_and_next_query.ftl">

			String sql = query.toString();

			Query q = session.createQuery(sql);

			q.setFirstResult(0);
			q.setMaxResults(2);

			QueryPos qPos = QueryPos.getInstance(q);

			<#include "persistence_impl_finder_qpos.ftl">

			if (orderByComparator != null) {
				Object[] values = orderByComparator.getOrderByConditionValues(${entity.varName});

				for (Object value : values) {
					qPos.add(value);
				}
			}

			List<${entity.name}> list = q.list();

			if (list.size() == 2) {
				return list.get(1);
			}
			else {
				return null;
			}
		}
	</#if>

	<#if entity.isPermissionCheckEnabled(finder)>
		/**
		 * Returns all the ${entity.humanNames} that the user has permission to view where ${finder.getHumanConditions(false)}.
		 *
		<#list finderColsList as finderCol>
		 * @param ${finderCol.name} the ${finderCol.humanName}
		</#list>
		 * @return the matching ${entity.humanNames} that the user has permission to view
		 * @throws SystemException if a system exception occurred
		 */
		@Override
		public List<${entity.name}> filterFindBy${finder.name}(

		<#list finderColsList as finderCol>
			${finderCol.type} ${finderCol.name}

			<#if finderCol_has_next>
				,
			</#if>
		</#list>

		) throws SystemException {
			return filterFindBy${finder.name}(

			<#list finderColsList as finderCol>
				${finderCol.name},
			</#list>

			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		}

		/**
		 * Returns a range of all the ${entity.humanNames} that the user has permission to view where ${finder.getHumanConditions(false)}.
		 *
		 * <p>
		 * <#include "range_comment.ftl">
		 * </p>
		 *
		<#list finderColsList as finderCol>
		 * @param ${finderCol.name} the ${finderCol.humanName}
		</#list>
		 * @param start the lower bound of the range of ${entity.humanNames}
		 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
		 * @return the range of matching ${entity.humanNames} that the user has permission to view
		 * @throws SystemException if a system exception occurred
		 */
		@Override
		public List<${entity.name}> filterFindBy${finder.name}(

		<#list finderColsList as finderCol>
			${finderCol.type} ${finderCol.name},
		</#list>

		int start, int end) throws SystemException {
			return filterFindBy${finder.name}(

			<#list finderColsList as finderCol>
				${finderCol.name},
			</#list>

			start, end, null);
		}

		/**
		 * Returns an ordered range of all the ${entity.humanNames} that the user has permissions to view where ${finder.getHumanConditions(false)}.
		 *
		 * <p>
		 * <#include "range_comment.ftl">
		 * </p>
		 *
		<#list finderColsList as finderCol>
		 * @param ${finderCol.name} the ${finderCol.humanName}
		</#list>
		 * @param start the lower bound of the range of ${entity.humanNames}
		 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
		 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
		 * @return the ordered range of matching ${entity.humanNames} that the user has permission to view
		 * @throws SystemException if a system exception occurred
		 */
		@Override
		public List<${entity.name}> filterFindBy${finder.name}(

		<#list finderColsList as finderCol>
			${finderCol.type} ${finderCol.name},
		</#list>

		int start, int end, OrderByComparator orderByComparator) throws SystemException {
			if (!InlineSQLHelperUtil.isEnabled(<#if finder.hasColumn("groupId")>groupId</#if>)) {
				return findBy${finder.name}(

				<#list finderColsList as finderCol>
					${finderCol.name},
				</#list>

				start, end, orderByComparator);
			}

			<#if entity.isPermissionedModel()>
				<#include "persistence_impl_find_by_query.ftl">

				String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(), ${entity.name}.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, _FILTER_ENTITY_TABLE_FILTER_USERID_COLUMN<#if finder.hasColumn("groupId")>, groupId</#if>);

				Session session = null;

				try {
					session = openSession();

					Query q = session.createQuery(sql);

					QueryPos qPos = QueryPos.getInstance(q);

					<#include "persistence_impl_finder_qpos.ftl">

					return (List<${entity.name}>)QueryUtil.list(q, getDialect(), start, end);
				}
				catch (Exception e) {
					throw processException(e);
				}
				finally {
					closeSession(session);
				}
			<#else>
				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(${finderColsList?size + 2} + (orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(${finderColsList?size + 2});
				}

				if (getDB().isSupportsInlineDistinct()) {
					query.append(_FILTER_SQL_SELECT_${entity.alias?upper_case}_WHERE);
				}
				else {
					query.append(_FILTER_SQL_SELECT_${entity.alias?upper_case}_NO_INLINE_DISTINCT_WHERE_1);
				}

				<#assign sqlQuery = true>

				<#include "persistence_impl_finder_cols.ftl">

				<#assign sqlQuery = false>

				if (!getDB().isSupportsInlineDistinct()) {
					query.append(_FILTER_SQL_SELECT_${entity.alias?upper_case}_NO_INLINE_DISTINCT_WHERE_2);
				}

				if (orderByComparator != null) {
					if (getDB().isSupportsInlineDistinct()) {
						appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
					}
					else {
						appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
					}
				}
				else {
					if (getDB().isSupportsInlineDistinct()) {
						query.append(${entity.name}ModelImpl.ORDER_BY_JPQL);
					}
					else {
						query.append(${entity.name}ModelImpl.ORDER_BY_SQL);
					}
				}

				String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(), ${entity.name}.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN<#if finder.hasColumn("groupId")>, groupId</#if>);

				Session session = null;

				try {
					session = openSession();

					SQLQuery q = session.createSynchronizedSQLQuery(sql);

					if (getDB().isSupportsInlineDistinct()) {
						q.addEntity(_FILTER_ENTITY_ALIAS, ${entity.name}Impl.class);
					}
					else {
						q.addEntity(_FILTER_ENTITY_TABLE, ${entity.name}Impl.class);
					}

					QueryPos qPos = QueryPos.getInstance(q);

					<#include "persistence_impl_finder_qpos.ftl">

					return (List<${entity.name}>)QueryUtil.list(q, getDialect(), start, end);
				}
				catch (Exception e) {
					throw processException(e);
				}
				finally {
					closeSession(session);
				}
			</#if>
		}

		<#if !finder.hasColumn(entity.PKVarName)>
			/**
			 * Returns the ${entity.humanNames} before and after the current ${entity.humanName} in the ordered set of ${entity.humanNames} that the user has permission to view where ${finder.getHumanConditions(false)}.
			 *
			 * @param ${entity.PKVarName} the primary key of the current ${entity.humanName}
			<#list finderColsList as finderCol>
			 * @param ${finderCol.name} the ${finderCol.humanName}
			</#list>
			 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
			 * @return the previous, current, and next ${entity.humanName}
			 * @throws ${packagePath}.${noSuchEntity}Exception if a ${entity.humanName} with the primary key could not be found
			 * @throws SystemException if a system exception occurred
			 */
			@Override
			public ${entity.name}[] filterFindBy${finder.name}_PrevAndNext(${entity.PKClassName} ${entity.PKVarName},

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name},
			</#list>

			OrderByComparator orderByComparator) throws ${noSuchEntity}Exception, SystemException {
				if (!InlineSQLHelperUtil.isEnabled(<#if finder.hasColumn("groupId")>groupId</#if>)) {
					return findBy${finder.name}_PrevAndNext(${entity.PKVarName},

					<#list finderColsList as finderCol>
						${finderCol.name},
					</#list>

					orderByComparator);
				}

				${entity.name} ${entity.varName} = findByPrimaryKey(${entity.PKVarName});

				Session session = null;

				try {
					session = openSession();

					${entity.name}[] array = new ${entity.name}Impl[3];

					array[0] =
						filterGetBy${finder.name}_PrevAndNext(
							session, ${entity.varName},

							<#list finderColsList as finderCol>
								${finderCol.name},
							</#list>

							orderByComparator, true);

					array[1] = ${entity.varName};

					array[2] =
						filterGetBy${finder.name}_PrevAndNext(
							session, ${entity.varName},

							<#list finderColsList as finderCol>
								${finderCol.name},
							</#list>

							orderByComparator, false);

					return array;
				}
				catch (Exception e) {
					throw processException(e);
				}
				finally {
					closeSession(session);
				}
			}

			protected ${entity.name} filterGetBy${finder.name}_PrevAndNext(
				Session session, ${entity.name} ${entity.varName},

				<#list finderColsList as finderCol>
					${finderCol.type} ${finderCol.name},
				</#list>

				OrderByComparator orderByComparator, boolean previous) {

				<#if entity.isPermissionedModel()>
					<#include "persistence_impl_get_by_prev_and_next_query.ftl">

					String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(), ${entity.name}.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, _FILTER_ENTITY_TABLE_FILTER_USERID_COLUMN<#if finder.hasColumn("groupId")>, groupId</#if>);

					Query q = session.createQuery(sql);

					q.setFirstResult(0);
					q.setMaxResults(2);

					QueryPos qPos = QueryPos.getInstance(q);

					<#include "persistence_impl_finder_qpos.ftl">

					if (orderByComparator != null) {
						Object[] values = orderByComparator.getOrderByConditionValues(${entity.varName});

						for (Object value : values) {
							qPos.add(value);
						}
					}

					List<${entity.name}> list = q.list();

					if (list.size() == 2) {
						return list.get(1);
					}
					else {
						return null;
					}
				<#else>
					StringBundler query = null;

					if (orderByComparator != null) {
						query = new StringBundler(6 + (orderByComparator.getOrderByFields().length * 6));
					}
					else {
						query = new StringBundler(3);
					}

					if (getDB().isSupportsInlineDistinct()) {
						query.append(_FILTER_SQL_SELECT_${entity.alias?upper_case}_WHERE);
					}
					else {
						query.append(_FILTER_SQL_SELECT_${entity.alias?upper_case}_NO_INLINE_DISTINCT_WHERE_1);
					}

					<#assign sqlQuery = true>

					<#include "persistence_impl_finder_cols.ftl">

					<#assign sqlQuery = false>

					if (!getDB().isSupportsInlineDistinct()) {
						query.append(_FILTER_SQL_SELECT_${entity.alias?upper_case}_NO_INLINE_DISTINCT_WHERE_2);
					}

					if (orderByComparator != null) {
						String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

						if (orderByConditionFields.length > 0) {
							query.append(WHERE_AND);
						}

						for (int i = 0; i < orderByConditionFields.length; i++) {
							if (getDB().isSupportsInlineDistinct()) {
								query.append(_ORDER_BY_ENTITY_ALIAS);
							}
							else {
								query.append(_ORDER_BY_ENTITY_TABLE);
							}

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
							if (getDB().isSupportsInlineDistinct()) {
								query.append(_ORDER_BY_ENTITY_ALIAS);
							}
							else {
								query.append(_ORDER_BY_ENTITY_TABLE);
							}

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
						if (getDB().isSupportsInlineDistinct()) {
							query.append(${entity.name}ModelImpl.ORDER_BY_JPQL);
						}
						else {
							query.append(${entity.name}ModelImpl.ORDER_BY_SQL);
						}
					}

					String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(), ${entity.name}.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN<#if finder.hasColumn("groupId")>, groupId</#if>);

					SQLQuery q = session.createSynchronizedSQLQuery(sql);

					q.setFirstResult(0);
					q.setMaxResults(2);

					if (getDB().isSupportsInlineDistinct()) {
						q.addEntity(_FILTER_ENTITY_ALIAS, ${entity.name}Impl.class);
					}
					else {
						q.addEntity(_FILTER_ENTITY_TABLE, ${entity.name}Impl.class);
					}

					QueryPos qPos = QueryPos.getInstance(q);

					<#include "persistence_impl_finder_qpos.ftl">

					if (orderByComparator != null) {
						Object[] values = orderByComparator.getOrderByConditionValues(${entity.varName});

						for (Object value : values) {
							qPos.add(value);
						}
					}

					List<${entity.name}> list = q.list();

					if (list.size() == 2) {
						return list.get(1);
					}
					else {
						return null;
					}
				</#if>
			}
		</#if>

		<#if finder.hasArrayableOperator()>
			/**
			 * Returns all the ${entity.humanNames} that the user has permission to view where ${finder.getHumanConditions(true)}.
			 *
			<#list finderColsList as finderCol>
				<#if finderCol.hasArrayableOperator()>
			 * @param ${finderCol.names} the ${finderCol.humanNames}
				<#else>
			 * @param ${finderCol.name} the ${finderCol.humanName}
				</#if>
			</#list>
			 * @return the matching ${entity.humanNames} that the user has permission to view
			 * @throws SystemException if a system exception occurred
			 */
			@Override
			public List<${entity.name}> filterFindBy${finder.name}(

			<#list finderColsList as finderCol>
				<#if finderCol.hasArrayableOperator()>
					${finderCol.type}[] ${finderCol.names}
				<#else>
					${finderCol.type} ${finderCol.name}
				</#if>

				<#if finderCol_has_next>
					,
				</#if>
			</#list>

			) throws SystemException {
				return filterFindBy${finder.name}(

				<#list finderColsList as finderCol>
					<#if finderCol.hasArrayableOperator()>
						${finderCol.names},
					<#else>
						${finderCol.name},
					</#if>
				</#list>

				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
			}

			/**
			 * Returns a range of all the ${entity.humanNames} that the user has permission to view where ${finder.getHumanConditions(true)}.
			 *
			 * <p>
			 * <#include "range_comment.ftl">
			 * </p>
			 *
			<#list finderColsList as finderCol>
				<#if finderCol.hasArrayableOperator()>
			 * @param ${finderCol.names} the ${finderCol.humanNames}
				<#else>
			 * @param ${finderCol.name} the ${finderCol.humanName}
				</#if>
			</#list>
			 * @param start the lower bound of the range of ${entity.humanNames}
			 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
			 * @return the range of matching ${entity.humanNames} that the user has permission to view
			 * @throws SystemException if a system exception occurred
			 */
			@Override
			public List<${entity.name}> filterFindBy${finder.name}(

			<#list finderColsList as finderCol>
				<#if finderCol.hasArrayableOperator()>
					${finderCol.type}[] ${finderCol.names},
				<#else>
					${finderCol.type} ${finderCol.name},
				</#if>
			</#list>

			int start, int end) throws SystemException {
				return filterFindBy${finder.name}(

				<#list finderColsList as finderCol>
					<#if finderCol.hasArrayableOperator()>
						${finderCol.names},
					<#else>
						${finderCol.name},
					</#if>
				</#list>

				start, end, null);
			}

			/**
			 * Returns an ordered range of all the ${entity.humanNames} that the user has permission to view where ${finder.getHumanConditions(true)}.
			 *
			 * <p>
			 * <#include "range_comment.ftl">
			 * </p>
			 *
			<#list finderColsList as finderCol>
				<#if finderCol.hasArrayableOperator()>
			 * @param ${finderCol.names} the ${finderCol.humanNames}
				<#else>
			 * @param ${finderCol.name} the ${finderCol.humanName}
				</#if>
			</#list>
			 * @param start the lower bound of the range of ${entity.humanNames}
			 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
			 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
			 * @return the ordered range of matching ${entity.humanNames} that the user has permission to view
			 * @throws SystemException if a system exception occurred
			 */
			@Override
			public List<${entity.name}> filterFindBy${finder.name}(

			<#list finderColsList as finderCol>
				<#if finderCol.hasArrayableOperator()>
					${finderCol.type}[] ${finderCol.names},
				<#else>
					${finderCol.type} ${finderCol.name},
				</#if>
			</#list>

			int start, int end, OrderByComparator orderByComparator) throws SystemException {
				if (!InlineSQLHelperUtil.isEnabled(
					<#if finder.hasColumn("groupId")>
						<#if finder.getColumn("groupId").hasArrayableOperator()>
							groupIds
						<#else>
							groupId
						</#if>
					</#if>)) {

					return findBy${finder.name}(

					<#list finderColsList as finderCol>
						<#if finderCol.hasArrayableOperator()>
							${finderCol.names},
						<#else>
							${finderCol.name},
						</#if>
					</#list>

					start, end, orderByComparator);
				}

				<#if entity.isPermissionedModel()>
					<#include "persistence_impl_find_by_arrayable_query.ftl">

					String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(), ${entity.name}.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, _FILTER_ENTITY_TABLE_FILTER_USERID_COLUMN

					<#if finder.hasColumn("groupId")>,
						<#if finder.getColumn("groupId").hasArrayableOperator()>
							groupIds
						<#else>
							groupId
						</#if>
					</#if>);

					Session session = null;

					try {
						session = openSession();

						Query q = session.createQuery(sql);

						QueryPos qPos = QueryPos.getInstance(q);

						<#include "persistence_impl_finder_arrayable_qpos.ftl">

						return (List<${entity.name}>)QueryUtil.list(q, getDialect(), start, end);
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						closeSession(session);
					}
				<#else>
					StringBundler query = new StringBundler();

					if (getDB().isSupportsInlineDistinct()) {
						query.append(_FILTER_SQL_SELECT_${entity.alias?upper_case}_WHERE);
					}
					else {
						query.append(_FILTER_SQL_SELECT_${entity.alias?upper_case}_NO_INLINE_DISTINCT_WHERE_1);
					}

					<#assign sqlQuery = true>

					<#include "persistence_impl_finder_arrayable_cols.ftl">

					<#assign sqlQuery = false>

					if (!getDB().isSupportsInlineDistinct()) {
						query.append(_FILTER_SQL_SELECT_${entity.alias?upper_case}_NO_INLINE_DISTINCT_WHERE_2);
					}

					if (orderByComparator != null) {
						if (getDB().isSupportsInlineDistinct()) {
							appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
						}
						else {
							appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
						}
					}
					else {
						if (getDB().isSupportsInlineDistinct()) {
							query.append(${entity.name}ModelImpl.ORDER_BY_JPQL);
						}
						else {
							query.append(${entity.name}ModelImpl.ORDER_BY_SQL);
						}
					}

					String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(), ${entity.name}.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN

					<#if finder.hasColumn("groupId")>,
						<#if finder.getColumn("groupId").hasArrayableOperator()>
							groupIds
						<#else>
							groupId
						</#if>
					</#if>);

					Session session = null;

					try {
						session = openSession();

						SQLQuery q = session.createSynchronizedSQLQuery(sql);

						if (getDB().isSupportsInlineDistinct()) {
							q.addEntity(_FILTER_ENTITY_ALIAS, ${entity.name}Impl.class);
						}
						else {
							q.addEntity(_FILTER_ENTITY_TABLE, ${entity.name}Impl.class);
						}

						QueryPos qPos = QueryPos.getInstance(q);

						<#include "persistence_impl_finder_arrayable_qpos.ftl">

						return (List<${entity.name}>)QueryUtil.list(q, getDialect(), start, end);
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						closeSession(session);
					}
				</#if>
			}
		</#if>
	</#if>
</#if>

<#-- Case 4: !finder.isCollection() && !finder.isUnique() -->

<#if !finder.isCollection() && !finder.isUnique()>
</#if>

<#-- Case 5: finder.isUnique() -->

<#if finder.isUnique()>
</#if>

<#-- Case 6: !finder.isUnique() -->

<#if !finder.isUnique()>
</#if>

<#-- Case 7: finder.isCollection() -->

<#if finder.isCollection()>
	<#if finder.hasArrayableOperator()>
		/**
		 * Returns all the ${entity.humanNames} where ${finder.getHumanConditions(true)}.
		 *
		 * <p>
		 * <#include "range_comment.ftl">
		 * </p>
		 *
		<#list finderColsList as finderCol>
			<#if finderCol.hasArrayableOperator()>
		 * @param ${finderCol.names} the ${finderCol.humanNames}
			<#else>
		 * @param ${finderCol.name} the ${finderCol.humanName}
			</#if>
		</#list>
		 * @return the matching ${entity.humanNames}
		 * @throws SystemException if a system exception occurred
		 */
		@Override
		public List<${entity.name}> findBy${finder.name}(

		<#list finderColsList as finderCol>
			<#if finderCol.hasArrayableOperator()>
				${finderCol.type}[] ${finderCol.names}
			<#else>
				${finderCol.type} ${finderCol.name}
			</#if>

			<#if finderCol_has_next>
				,
			</#if>
		</#list>

		) throws SystemException {
			return findBy${finder.name}(

			<#list finderColsList as finderCol>
				<#if finderCol.hasArrayableOperator()>
					${finderCol.names},
				<#else>
					${finderCol.name},
				</#if>
			</#list>

			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		}

		/**
		 * Returns a range of all the ${entity.humanNames} where ${finder.getHumanConditions(true)}.
		 *
		 * <p>
		 * <#include "range_comment.ftl">
		 * </p>
		 *
		<#list finderColsList as finderCol>
			<#if finderCol.hasArrayableOperator()>
		 * @param ${finderCol.names} the ${finderCol.humanNames}
			<#else>
		 * @param ${finderCol.name} the ${finderCol.humanName}
			</#if>
		</#list>
		 * @param start the lower bound of the range of ${entity.humanNames}
		 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
		 * @return the range of matching ${entity.humanNames}
		 * @throws SystemException if a system exception occurred
		 */
		@Override
		public List<${entity.name}> findBy${finder.name}(

		<#list finderColsList as finderCol>
			<#if finderCol.hasArrayableOperator()>
				${finderCol.type}[] ${finderCol.names},
			<#else>
				${finderCol.type} ${finderCol.name},
			</#if>
		</#list>

		int start, int end) throws SystemException {
			return findBy${finder.name}(

			<#list finderColsList as finderCol>
				<#if finderCol.hasArrayableOperator()>
					${finderCol.names},
				<#else>
					${finderCol.name},
				</#if>
			</#list>

			start, end, null);
		}

		/**
		 * Returns an ordered range of all the ${entity.humanNames} where ${finder.getHumanConditions(true)}.
		 *
		 * <p>
		 * <#include "range_comment.ftl">
		 * </p>
		 *
		<#list finderColsList as finderCol>
			<#if finderCol.hasArrayableOperator()>
		 * @param ${finderCol.names} the ${finderCol.humanNames}
			<#else>
		 * @param ${finderCol.name} the ${finderCol.humanName}
			</#if>
		</#list>
		 * @param start the lower bound of the range of ${entity.humanNames}
		 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
		 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
		 * @return the ordered range of matching ${entity.humanNames}
		 * @throws SystemException if a system exception occurred
		 */
		@Override
		public List<${entity.name}> findBy${finder.name}(

		<#list finderColsList as finderCol>
			<#if finderCol.hasArrayableOperator()>
				${finderCol.type}[] ${finderCol.names},
			<#else>
				${finderCol.type} ${finderCol.name},
			</#if>
		</#list>

		int start, int end, OrderByComparator orderByComparator) throws SystemException {
			if (
			<#assign firstCol = true>
			<#list finderColsList as finderCol>
				<#if finderCol.hasArrayableOperator()>
					<#if firstCol>
						<#assign firstCol = false>
					<#else>
						&&
					</#if>

					(${finderCol.names} != null) && (${finderCol.names}.length == 1)
				</#if>
			</#list>
			) {
				<#if finder.isUnique()>
					${entity.name} ${entity.varName} = fetchBy${finder.name}(
						<#list finderColsList as finderCol>
							<#if finderCol.hasArrayableOperator()>
								${finderCol.names}[0]
							<#else>
								${finderCol.name}
							</#if>

							<#if finderCol_has_next>
								,
							</#if>
						</#list>);

					if (${entity.varName} == null) {
						return Collections.emptyList();
					}
					else {
						List<${entity.name}> list = new ArrayList<${entity.name}>(1);

						list.add(${entity.varName});

						return list;
					}
				<#else>
					return findBy${finder.name}(
						<#list finderColsList as finderCol>
							<#if finderCol.hasArrayableOperator()>
								${finderCol.names}[0],
							<#else>
								${finderCol.name},
							</#if>
						</#list>

						start, end, orderByComparator);
				</#if>
			}

			boolean pagination = true;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) && (orderByComparator == null)) {
				pagination = false;
				finderArgs = new Object[] {
					<#list finderColsList as finderCol>
						<#if finderCol.hasArrayableOperator()>
							StringUtil.merge(${finderCol.names})
						<#else>
							${finderCol.name}
						</#if>

						<#if finderCol_has_next>
							,
						</#if>
					</#list>
				};
			}
			else {
				finderArgs = new Object[] {
					<#list finderColsList as finderCol>
						<#if finderCol.hasArrayableOperator()>
							StringUtil.merge(${finderCol.names}),
						<#else>
							${finderCol.name},
						</#if>
					</#list>

					start, end, orderByComparator
				};
			}

			List<${entity.name}> list = (List<${entity.name}>)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_${finder.name?upper_case}, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (${entity.name} ${entity.varName} : list) {
					if (
						<#list finderColsList as finderCol>
							<#if finderCol.hasArrayableOperator()>
								!ArrayUtil.contains(${finderCol.names}, ${entity.varName}.get${finderCol.methodName}())
							<#else>
								<#if finderCol.isPrimitiveType(false)>
									(${finderCol.name} != ${entity.varName}.get${finderCol.methodName}())
								<#else>
									!Validator.equals(${finderCol.name}, ${entity.varName}.get${finderCol.methodName}())
								</#if>
							</#if>

							<#if finderCol_has_next>
								||
							</#if>
						</#list>
					) {
						list = null;

						break;
					}
				}
			}

			if (list == null) {
				<#assign checkPagination = true>

				<#include "persistence_impl_find_by_arrayable_query.ftl">

				<#assign checkPagination = false>

				String sql = query.toString();

				Session session = null;

				try {
					session = openSession();

					Query q = session.createQuery(sql);

					QueryPos qPos = QueryPos.getInstance(q);

					<#include "persistence_impl_finder_arrayable_qpos.ftl">

					if (!pagination) {
						list = (List<${entity.name}>)QueryUtil.list(q, getDialect(), start, end, false);

						Collections.sort(list);

						list = new UnmodifiableList<${entity.name}>(list);
					}
					else {
						list = (List<${entity.name}>)QueryUtil.list(q, getDialect(), start, end);
					}

					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_${finder.name?upper_case}, finderArgs, list);
				}
				catch (Exception e) {
					FinderCacheUtil.removeResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_${finder.name?upper_case}, finderArgs);

					throw processException(e);
				}
				finally {
					closeSession(session);
				}
			}

			return list;
		}
	</#if>
</#if>

<#-- Case 8: !finder.isCollection() -->

<#if !finder.isCollection()>
</#if>

<#-- Case 9: !finder.isCollection() || finder.isUnique() -->

<#if !finder.isCollection() || finder.isUnique()>
	/**
	 * Returns the ${entity.humanName} where ${finder.getHumanConditions(false)} or throws a {@link ${packagePath}.${noSuchEntity}Exception} if it could not be found.
	 *
	<#list finderColsList as finderCol>
	 * @param ${finderCol.name} the ${finderCol.humanName}
	</#list>
	 * @return the matching ${entity.humanName}
	 * @throws ${packagePath}.${noSuchEntity}Exception if a matching ${entity.humanName} could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ${entity.name} findBy${finder.name}(

	<#list finderColsList as finderCol>
		${finderCol.type} ${finderCol.name}

		<#if finderCol_has_next>
			,
		</#if>
	</#list>

	) throws ${noSuchEntity}Exception, SystemException {
		${entity.name} ${entity.varName} = fetchBy${finder.name}(

		<#list finderColsList as finderCol>
			${finderCol.name}

			<#if finderCol_has_next>
				,
			</#if>
		</#list>

		);

		if ( ${entity.varName} == null) {
			StringBundler msg = new StringBundler(${(finderColsList?size * 2) + 2});

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			<#list finderColsList as finderCol>
				msg.append("<#if finderCol_index != 0>, </#if>${finderCol.name}=");
				msg.append(${finderCol.name});

				<#if !finderCol_has_next>
					msg.append(StringPool.CLOSE_CURLY_BRACE);
				</#if>
			</#list>

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new ${noSuchEntity}Exception(msg.toString());
		}

		return ${entity.varName};
	}

	/**
	 * Returns the ${entity.humanName} where ${finder.getHumanConditions(false)} or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	<#list finderColsList as finderCol>
	 * @param ${finderCol.name} the ${finderCol.humanName}
	</#list>
	 * @return the matching ${entity.humanName}, or <code>null</code> if a matching ${entity.humanName} could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ${entity.name} fetchBy${finder.name}(

	<#list finderColsList as finderCol>
		${finderCol.type} ${finderCol.name}

		<#if finderCol_has_next>
			,
		</#if>
	</#list>

	) throws SystemException {
		return fetchBy${finder.name}(

		<#list finderColsList as finderCol>
			${finderCol.name},
		</#list>

		true);
	}

	/**
	 * Returns the ${entity.humanName} where ${finder.getHumanConditions(false)} or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	<#list finderColsList as finderCol>
	 * @param ${finderCol.name} the ${finderCol.humanName}
	</#list>
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching ${entity.humanName}, or <code>null</code> if a matching ${entity.humanName} could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ${entity.name} fetchBy${finder.name}(

	<#list finderColsList as finderCol>
		${finderCol.type} ${finderCol.name}

		,
	</#list>

	boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
			<#list finderColsList as finderCol>
				${finderCol.name}

				<#if finderCol_has_next>
					,
				</#if>
			</#list>
		};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_${finder.name?upper_case}, finderArgs, this);
		}

		if (result instanceof ${entity.name}) {
			${entity.name} ${entity.varName} = (${entity.name})result;

			if (
				<#list finderColsList as finderCol>
					<#if finderCol.isPrimitiveType(false)>
						(${finderCol.name} != ${entity.varName}.get${finderCol.methodName}())
					<#else>
						!Validator.equals(${finderCol.name}, ${entity.varName}.get${finderCol.methodName}())
					</#if>

					<#if finderCol_has_next>
						||
					</#if>
				</#list>
			) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(${finderColsList?size + 2});

			query.append(_SQL_SELECT_${entity.alias?upper_case}_WHERE);

			<#include "persistence_impl_finder_cols.ftl">

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				<#include "persistence_impl_finder_qpos.ftl">

				List<${entity.name}> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_${finder.name?upper_case}, finderArgs, list);
				}
				else {
					<#if !finder.isUnique()>
						if ((list.size() > 1) && _log.isWarnEnabled()) {
							_log.warn("${entity.name}PersistenceImpl.fetchBy${finder.name}(<#list finderColsList as finderCol>${finderCol.type}, </#list>boolean) with parameters (" + StringUtil.merge(finderArgs) + ") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					</#if>

					${entity.name} ${entity.varName} = list.get(0);

					result = ${entity.varName};

					cacheResult(${entity.varName});

					if (
						<#list finderColsList as finderCol>
							<#if finderCol.isPrimitiveType()>
								(${entity.varName}.get${finderCol.methodName}() != ${finderCol.name})
							<#else>
								(${entity.varName}.get${finderCol.methodName}() == null) || !${entity.varName}.get${finderCol.methodName}().equals(${finderCol.name})
							</#if>

							<#if finderCol_has_next>
								||
							</#if>
						</#list>
					) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_${finder.name?upper_case}, finderArgs, ${entity.varName});
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_${finder.name?upper_case}, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (${entity.name})result;
		}
	}
</#if>
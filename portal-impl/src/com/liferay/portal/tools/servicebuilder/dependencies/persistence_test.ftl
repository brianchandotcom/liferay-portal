<#assign parentPKColumn = "">

<#if entity.isHierarchicalTree()>
	<#if entity.hasColumn("groupId")>
		<#assign scopeColumn = entity.getColumn("groupId")>
	<#else>
		<#assign scopeColumn = entity.getColumn("companyId")>
	</#if>

	<#assign pkColumn = entity.getPKList()?first>

	<#assign parentPKColumn = entity.getColumn("parent" + pkColumn.methodName)>
</#if>

package ${packagePath}.service.persistence;

<#assign noSuchEntity = serviceBuilder.getNoSuchEntityException(entity)>

import ${packagePath}.${noSuchEntity}Exception;
import ${packagePath}.model.${entity.name};
import ${packagePath}.model.impl.${entity.name}ModelImpl;

import ${beanLocatorUtil};
import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.io.Serializable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.Test;

@ExecutionTestListeners(listeners = {PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class ${entity.name}PersistenceTest {

	@After
	public void tearDown() throws Exception {
		Map<Serializable, BasePersistence<?>> basePersistences = _transactionalPersistenceAdvice.getBasePersistences();

		Set<Serializable> primaryKeys = basePersistences.keySet();

		for (Serializable primaryKey : primaryKeys) {
			BasePersistence<?> basePersistence = basePersistences.get(primaryKey);

			try {
				basePersistence.remove(primaryKey);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("The model with primary key " + primaryKey + " was already deleted");
				}
			}
		}

		_transactionalPersistenceAdvice.reset();
	}

	@Test
	public void testCreate() throws Exception {
		<#if entity.hasCompoundPK()>
			${entity.PKClassName} pk = new ${entity.PKClassName}(

			<#list entity.PKList as column>
				<#if column.type == "int">
					ServiceTestUtil.nextInt()
				<#elseif column.type == "long">
					ServiceTestUtil.nextLong()
				<#elseif column.type == "String">
					ServiceTestUtil.randomString()
				</#if>

				<#if column_has_next>
					,
				</#if>
			</#list>

			);
		<#else>
			<#assign column = entity.PKList[0]>

			${column.type} pk =

			<#if column.type == "int">
				ServiceTestUtil.nextInt()
			<#elseif column.type == "long">
				ServiceTestUtil.nextLong()
			<#elseif column.type == "String">
				ServiceTestUtil.randomString()
			</#if>

			;
		</#if>

		${entity.name} ${entity.varName} = _persistence.create(pk);

		Assert.assertNotNull(${entity.varName});

		Assert.assertEquals(${entity.varName}.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		${entity.name} new${entity.name} = add${entity.name}();

		_persistence.remove(new${entity.name});

		${entity.name} existing${entity.name} = _persistence.fetchByPrimaryKey(new${entity.name}.getPrimaryKey());

		Assert.assertNull(existing${entity.name});
	}

	@Test
	public void testUpdateNew() throws Exception {
		add${entity.name}();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		<#if entity.hasCompoundPK()>
			${entity.PKClassName} pk = new ${entity.PKClassName}(

			<#list entity.PKList as column>
				<#if column.type == "int">
					ServiceTestUtil.nextInt()
				<#elseif column.type == "long">
					ServiceTestUtil.nextLong()
				<#elseif column.type == "String">
					ServiceTestUtil.randomString()
				</#if>

				<#if column_has_next>
					,
				</#if>
			</#list>

			);
		<#else>
			<#assign column = entity.PKList[0]>

			${column.type} pk =

			<#if column.type == "int">
				ServiceTestUtil.nextInt()
			<#elseif column.type == "long">
				ServiceTestUtil.nextLong()
			<#elseif column.type == "String">
				ServiceTestUtil.randomString()
			</#if>

			;
		</#if>

		${entity.name} new${entity.name} = _persistence.create(pk);

		<#list entity.regularColList as column>
			<#if !column.primary && ((parentPKColumn == "") || (parentPKColumn.name != column.name))>
				<#if column.type == "Blob">
					String new${column.methodName}String = ServiceTestUtil.randomString();

					byte[] new${column.methodName}Bytes = new${column.methodName}String.getBytes(StringPool.UTF8);

					Blob new${column.methodName}Blob = new OutputBlob(new UnsyncByteArrayInputStream(new${column.methodName}Bytes), new${column.methodName}Bytes.length);
				</#if>

				new${entity.name}.set${column.methodName}(

				<#if column.type == "boolean">
					ServiceTestUtil.randomBoolean()
				<#elseif column.type == "double">
					ServiceTestUtil.nextDouble()
				<#elseif column.type == "int">
					ServiceTestUtil.nextInt()
				<#elseif column.type == "long">
					ServiceTestUtil.nextLong()
				<#elseif column.type == "Date">
					ServiceTestUtil.nextDate()
				<#elseif column.type == "Blob">
					 new${column.methodName}Blob
				<#elseif column.type == "String">
					ServiceTestUtil.randomString()
				</#if>

				);
			</#if>
		</#list>

		_persistence.update(new${entity.name});

		${entity.name} existing${entity.name} = _persistence.findByPrimaryKey(new${entity.name}.getPrimaryKey());

		<#list entity.regularColList as column>
			<#if column.type == "Blob">
				Blob existing${column.methodName} = existing${entity.name}.get${column.methodName}();

				Assert.assertTrue(Arrays.equals(existing${column.methodName}.getBytes(1, (int)existing${column.methodName}.length()), new${column.methodName}Bytes));
			<#elseif column.type == "Date">
				Assert.assertEquals(Time.getShortTimestamp(existing${entity.name}.get${column.methodName}()), Time.getShortTimestamp(new${entity.name}.get${column.methodName}()));
			<#elseif column.type == "double">
				AssertUtils.assertEquals(existing${entity.name}.get${column.methodName}(), new${entity.name}.get${column.methodName}());
			<#else>
				Assert.assertEquals(existing${entity.name}.get${column.methodName}(), new${entity.name}.get${column.methodName}());
			</#if>
		</#list>
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		${entity.name} new${entity.name} = add${entity.name}();

		${entity.name} existing${entity.name} = _persistence.findByPrimaryKey(new${entity.name}.getPrimaryKey());

		Assert.assertEquals(existing${entity.name}, new${entity.name});
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		<#if entity.hasCompoundPK()>
			${entity.PKClassName} pk = new ${entity.PKClassName}(

			<#list entity.PKList as column>
				<#if column.type == "int">
					ServiceTestUtil.nextInt()
				<#elseif column.type == "long">
					ServiceTestUtil.nextLong()
				<#elseif column.type == "String">
					ServiceTestUtil.randomString()
				</#if>

				<#if column_has_next>
					,
				</#if>
			</#list>

			);
		<#else>
			<#assign column = entity.PKList[0]>

			${column.type} pk =

			<#if column.type == "int">
				ServiceTestUtil.nextInt()
			<#elseif column.type == "long">
				ServiceTestUtil.nextLong()
			<#elseif column.type == "String">
				ServiceTestUtil.randomString()
			</#if>

			;
		</#if>

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw ${noSuchEntity}Exception");
		}
		catch (${noSuchEntity}Exception nsee) {
		}
	}

	<#if !entity.hasCompoundPK()>
		@Test
		public void testFindAll() throws Exception {
			try {
				_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
			}
			catch (Exception e) {
				Assert.fail(e.getMessage());
			}
		}

		<#list entity.getFinderList() as finder>
			<#if (finder.name == "GroupId") && entity.isPermissionCheckEnabled(finder)>
				@Test
				public void testFilterFindByGroupId() throws Exception {
					try {
						_persistence.filterFindByGroupId(0, QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
					}
					catch (Exception e) {
						Assert.fail(e.getMessage());
					}
				}

				<#break>
			</#if>
		</#list>

		protected OrderByComparator getOrderByComparator() {
			return OrderByComparatorFactoryUtil.create(
				"${entity.table}",

				<#list entity.regularColList as column>
					<#if column.type != "Blob">
						"${column.name}", true

						<#if column_has_next>
							,
						</#if>
					</#if>
				</#list>

				);
		}
	</#if>

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		${entity.name} new${entity.name} = add${entity.name}();

		${entity.name} existing${entity.name} = _persistence.fetchByPrimaryKey(new${entity.name}.getPrimaryKey());

		Assert.assertEquals(existing${entity.name}, new${entity.name});
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		<#if entity.hasCompoundPK()>
			${entity.PKClassName} pk = new ${entity.PKClassName}(

			<#list entity.PKList as column>
				<#if column.type == "int">
					ServiceTestUtil.nextInt()
				<#elseif column.type == "long">
					ServiceTestUtil.nextLong()
				<#elseif column.type == "String">
					ServiceTestUtil.randomString()
				</#if>

				<#if column_has_next>
					,
				</#if>
			</#list>

			);
		<#else>
			<#assign column = entity.PKList[0]>

			${column.type} pk =

			<#if column.type == "int">
				ServiceTestUtil.nextInt()
			<#elseif column.type == "long">
				ServiceTestUtil.nextLong()
			<#elseif column.type == "String">
				ServiceTestUtil.randomString()
			</#if>

			;
		</#if>

		${entity.name} missing${entity.name} = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missing${entity.name});
	}

	<#if entity.hasActionableDynamicQuery()>
		@Test
		public void testActionableDynamicQuery() throws Exception {
			final IntegerWrapper count = new IntegerWrapper();

			ActionableDynamicQuery actionableDynamicQuery = new ${entity.name}ActionableDynamicQuery() {

				@Override
				protected void performAction(Object object) {
					${entity.name} ${entity.varName} = (${entity.name})object;

					Assert.assertNotNull(${entity.varName});

					count.increment();
				}

			};

			actionableDynamicQuery.performActions();

			Assert.assertEquals(count.getValue(), _persistence.countAll());
		}
	</#if>

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		${entity.name} new${entity.name} = add${entity.name}();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(${entity.name}.class, ${entity.name}.class.getClassLoader());

		<#if entity.hasCompoundPK()>
			<#list entity.PKList as column>
				dynamicQuery.add(RestrictionsFactoryUtil.eq("id.${column.name}", new${entity.name}.get${column.methodName}()));
			</#list>
		<#else>
			<#assign column = entity.PKList[0]>

			dynamicQuery.add(RestrictionsFactoryUtil.eq("${column.name}", new${entity.name}.get${column.methodName}()));
		</#if>

		List<${entity.name}> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		${entity.name} existing${entity.name} = result.get(0);

		Assert.assertEquals(existing${entity.name}, new${entity.name});
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(${entity.name}.class, ${entity.name}.class.getClassLoader());

		<#if entity.hasCompoundPK()>
			<#list entity.PKList as column>
				dynamicQuery.add(RestrictionsFactoryUtil.eq("id.${column.name}",

				<#if column.type == "int">
					ServiceTestUtil.nextInt()
				<#elseif column.type == "long">
					ServiceTestUtil.nextLong()
				<#elseif column.type == "String">
					ServiceTestUtil.randomString()
				</#if>

				));
			</#list>
		<#else>
			<#assign column = entity.PKList[0]>

			dynamicQuery.add(RestrictionsFactoryUtil.eq("${column.name}",

			<#if column.type == "int">
				ServiceTestUtil.nextInt()
			<#elseif column.type == "long">
				ServiceTestUtil.nextLong()
			<#elseif column.type == "String">
				ServiceTestUtil.randomString()
			</#if>

			));
		</#if>

		List<${entity.name}> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		${entity.name} new${entity.name} = add${entity.name}();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(${entity.name}.class, ${entity.name}.class.getClassLoader());

		<#assign column = entity.PKList[0]>

		<#if entity.hasCompoundPK()>
			<#assign propertyName = "id.${column.name}">
		<#else>
			<#assign propertyName = "${column.name}">
		</#if>

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("${propertyName}"));

		Object new${column.methodName} = new${entity.name}.get${column.methodName}();

		dynamicQuery.add(RestrictionsFactoryUtil.in("${propertyName}", new Object[] {new${column.methodName}}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existing${column.methodName} = result.get(0);

		Assert.assertEquals(existing${column.methodName}, new${column.methodName});
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(${entity.name}.class, ${entity.name}.class.getClassLoader());

		<#assign column = entity.PKList[0]>

		<#if entity.hasCompoundPK()>
			<#assign propertyName = "id.${column.name}">
		<#else>
			<#assign propertyName = "${column.name}">
		</#if>

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("${propertyName}"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("${propertyName}", new Object[] {

		<#if column.type == "int">
			ServiceTestUtil.nextInt()
		<#elseif column.type == "long">
			ServiceTestUtil.nextLong()
		<#elseif column.type == "String">
			ServiceTestUtil.randomString()
		</#if>

		}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	<#assign uniqueFinderList = entity.getUniqueFinderList()>

	<#if uniqueFinderList?size != 0>
		@Test
		public void testResetOriginalValues() throws Exception {
			if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
				return;
			}

			${entity.name} new${entity.name} = add${entity.name}();

			_persistence.clearCache();

			${entity.name}ModelImpl existing${entity.name}ModelImpl = (${entity.name}ModelImpl)_persistence.findByPrimaryKey(new${entity.name}.getPrimaryKey());

			<#list uniqueFinderList as finder>
				<#assign finderColsList = finder.getColumns()>

				<#list finderColsList as finderCol>
					<#if finderCol.type == "double">
						AssertUtils.assertEquals(existing${entity.name}ModelImpl.get${finderCol.methodName}(), existing${entity.name}ModelImpl.getOriginal${finderCol.methodName}());
					<#elseif finderCol.isPrimitiveType()>
						Assert.assertEquals(existing${entity.name}ModelImpl.get${finderCol.methodName}(), existing${entity.name}ModelImpl.getOriginal${finderCol.methodName}());
					<#else>
						Assert.assertTrue(Validator.equals(existing${entity.name}ModelImpl.get${finderCol.methodName}(), existing${entity.name}ModelImpl.getOriginal${finderCol.methodName}()));
					</#if>
				</#list>
			</#list>
		}
	</#if>

	protected ${entity.name} add${entity.name}() throws Exception {
		<#if entity.hasCompoundPK()>
			${entity.PKClassName} pk = new ${entity.PKClassName}(

			<#list entity.PKList as column>
				<#if column.type == "int">
					ServiceTestUtil.nextInt()
				<#elseif column.type == "long">
					ServiceTestUtil.nextLong()
				<#elseif column.type == "String">
					ServiceTestUtil.randomString()
				</#if>

				<#if column_has_next>
					,
				</#if>
			</#list>

			);
		<#else>
			<#assign column = entity.PKList[0]>

			${column.type} pk =

			<#if column.type == "int">
				ServiceTestUtil.nextInt()
			<#elseif column.type == "long">
				ServiceTestUtil.nextLong()
			<#elseif column.type == "String">
				ServiceTestUtil.randomString()
			</#if>

			;
		</#if>

		${entity.name} ${entity.varName} = _persistence.create(pk);

		<#list entity.regularColList as column>
			<#if !column.primary && ((parentPKColumn == "") || (parentPKColumn.name != column.name))>
				<#if column.type == "Blob">
					String ${column.name}String = ServiceTestUtil.randomString();

					byte[] ${column.name}Bytes = ${column.name}String.getBytes(StringPool.UTF8);

					Blob ${column.name}Blob = new OutputBlob(new UnsyncByteArrayInputStream(${column.name}Bytes), ${column.name}Bytes.length);
				</#if>

				${entity.varName}.set${column.methodName}(

				<#if column.type == "boolean">
					ServiceTestUtil.randomBoolean()
				<#elseif column.type == "double">
					ServiceTestUtil.nextDouble()
				<#elseif column.type == "int">
					ServiceTestUtil.nextInt()
				<#elseif column.type == "long">
					ServiceTestUtil.nextLong()
				<#elseif column.type == "Blob">
					${column.name}Blob
				<#elseif column.type == "Date">
					ServiceTestUtil.nextDate()
				<#elseif column.type == "String">
					ServiceTestUtil.randomString()
				</#if>

				);
			</#if>
		</#list>

		_persistence.update(${entity.varName});

		return ${entity.varName};
	}

	<#if entity.isHierarchicalTree()>
		@Test
		public void testMoveTree() throws Exception {
			long ${scopeColumn.name} = ServiceTestUtil.nextLong();

			${entity.name} root${entity.name} = add${entity.name}(${scopeColumn.name}, null);

			long previousRootLeft${pkColumn.methodName} = root${entity.name}.getLeft${pkColumn.methodName}();
			long previousRootRight${pkColumn.methodName} = root${entity.name}.getRight${pkColumn.methodName}();

			${entity.name} child${entity.name} = add${entity.name}(${scopeColumn.name}, root${entity.name}.get${pkColumn.methodName}());

			root${entity.name} = _persistence.fetchByPrimaryKey(root${entity.name}.getPrimaryKey());

			Assert.assertEquals(previousRootLeft${pkColumn.methodName}, root${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(previousRootRight${pkColumn.methodName} + 2, root${entity.name}.getRight${pkColumn.methodName}());
			Assert.assertEquals(root${entity.name}.getLeft${pkColumn.methodName}() + 1, child${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(root${entity.name}.getRight${pkColumn.methodName}() - 1, child${entity.name}.getRight${pkColumn.methodName}());
		}

		@Test
		public void testMoveTreeFromLeft() throws Exception {
			long ${scopeColumn.name} = ServiceTestUtil.nextLong();

			${entity.name} parent${entity.name} = add${entity.name}(${scopeColumn.name}, null);

			${entity.name} child${entity.name} = add${entity.name}(${scopeColumn.name}, parent${entity.name}.get${pkColumn.methodName}());

			parent${entity.name} = _persistence.fetchByPrimaryKey(parent${entity.name}.getPrimaryKey());

			${entity.name} root${entity.name} = add${entity.name}(${scopeColumn.name}, null);

			long previousRootLeft${pkColumn.methodName} = root${entity.name}.getLeft${pkColumn.methodName}();
			long previousRootRight${pkColumn.methodName} = root${entity.name}.getRight${pkColumn.methodName}();

			parent${entity.name}.setParent${pkColumn.methodName}(root${entity.name}.get${pkColumn.methodName}());

			_persistence.update(parent${entity.name});

			root${entity.name} = _persistence.fetchByPrimaryKey(root${entity.name}.getPrimaryKey());
			child${entity.name} = _persistence.fetchByPrimaryKey(child${entity.name}.getPrimaryKey());

			Assert.assertEquals(previousRootLeft${pkColumn.methodName} - 4, root${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(previousRootRight${pkColumn.methodName}, root${entity.name}.getRight${pkColumn.methodName}());
			Assert.assertEquals(root${entity.name}.getLeft${pkColumn.methodName}() + 1, parent${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(root${entity.name}.getRight${pkColumn.methodName}() - 1, parent${entity.name}.getRight${pkColumn.methodName}());
			Assert.assertEquals(parent${entity.name}.getLeft${pkColumn.methodName}() + 1, child${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(parent${entity.name}.getRight${pkColumn.methodName}() - 1, child${entity.name}.getRight${pkColumn.methodName}());
		}

		@Test
		public void testMoveTreeFromRight() throws Exception {
			long ${scopeColumn.name} = ServiceTestUtil.nextLong();

			${entity.name} root${entity.name} = add${entity.name}(${scopeColumn.name}, null);

			long previousRootLeft${pkColumn.methodName} = root${entity.name}.getLeft${pkColumn.methodName}();
			long previousRootRight${pkColumn.methodName} = root${entity.name}.getRight${pkColumn.methodName}();

			${entity.name} parent${entity.name} = add${entity.name}(${scopeColumn.name}, null);

			${entity.name} child${entity.name} = add${entity.name}(${scopeColumn.name}, parent${entity.name}.get${pkColumn.methodName}());

			parent${entity.name} = _persistence.fetchByPrimaryKey(parent${entity.name}.getPrimaryKey());

			parent${entity.name}.setParent${pkColumn.methodName}(root${entity.name}.get${pkColumn.methodName}());

			_persistence.update(parent${entity.name});

			root${entity.name} = _persistence.fetchByPrimaryKey(root${entity.name}.getPrimaryKey());
			child${entity.name} = _persistence.fetchByPrimaryKey(child${entity.name}.getPrimaryKey());

			Assert.assertEquals(previousRootLeft${pkColumn.methodName}, root${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(previousRootRight${pkColumn.methodName} + 4, root${entity.name}.getRight${pkColumn.methodName}());
			Assert.assertEquals(root${entity.name}.getLeft${pkColumn.methodName}() + 1, parent${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(root${entity.name}.getRight${pkColumn.methodName}() - 1, parent${entity.name}.getRight${pkColumn.methodName}());
			Assert.assertEquals(parent${entity.name}.getLeft${pkColumn.methodName}() + 1, child${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(parent${entity.name}.getRight${pkColumn.methodName}() - 1, child${entity.name}.getRight${pkColumn.methodName}());
		}

		@Test
		public void testMoveTreeIntoTreeFromLeft() throws Exception {
			long ${scopeColumn.name} = ServiceTestUtil.nextLong();

			${entity.name} parent${entity.name} = add${entity.name}(${scopeColumn.name}, null);

			${entity.name} parentChild${entity.name} = add${entity.name}(${scopeColumn.name}, parent${entity.name}.get${pkColumn.methodName}());

			parent${entity.name} = _persistence.fetchByPrimaryKey(parent${entity.name}.getPrimaryKey());

			${entity.name} root${entity.name} = add${entity.name}(${scopeColumn.name}, null);

			${entity.name} leftRootChild${entity.name} = add${entity.name}(${scopeColumn.name}, root${entity.name}.get${pkColumn.methodName}());

			root${entity.name} = _persistence.fetchByPrimaryKey(root${entity.name}.getPrimaryKey());

			${entity.name} rightRootChild${entity.name} = add${entity.name}(${scopeColumn.name}, root${entity.name}.get${pkColumn.methodName}());

			root${entity.name} = _persistence.fetchByPrimaryKey(root${entity.name}.getPrimaryKey());

			long previousRootLeft${pkColumn.methodName} = root${entity.name}.getLeft${pkColumn.methodName}();
			long previousRootRight${pkColumn.methodName} = root${entity.name}.getRight${pkColumn.methodName}();

			parent${entity.name}.setParent${pkColumn.methodName}(rightRootChild${entity.name}.get${pkColumn.methodName}());

			_persistence.update(parent${entity.name});

			root${entity.name} = _persistence.fetchByPrimaryKey(root${entity.name}.getPrimaryKey());
			leftRootChild${entity.name} = _persistence.fetchByPrimaryKey(leftRootChild${entity.name}.getPrimaryKey());
			rightRootChild${entity.name} = _persistence.fetchByPrimaryKey(rightRootChild${entity.name}.getPrimaryKey());
			parentChild${entity.name} = _persistence.fetchByPrimaryKey(parentChild${entity.name}.getPrimaryKey());

			Assert.assertEquals(previousRootLeft${pkColumn.methodName} - 4, root${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(previousRootRight${pkColumn.methodName}, root${entity.name}.getRight${pkColumn.methodName}());
			Assert.assertEquals(root${entity.name}.getLeft${pkColumn.methodName}() + 1, leftRootChild${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(root${entity.name}.getRight${pkColumn.methodName}() - 7, leftRootChild${entity.name}.getRight${pkColumn.methodName}());
			Assert.assertEquals(root${entity.name}.getLeft${pkColumn.methodName}() + 3, rightRootChild${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(root${entity.name}.getRight${pkColumn.methodName}() - 1, rightRootChild${entity.name}.getRight${pkColumn.methodName}());
			Assert.assertEquals(rightRootChild${entity.name}.getLeft${pkColumn.methodName}() + 1, parent${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(rightRootChild${entity.name}.getRight${pkColumn.methodName}() - 1, parent${entity.name}.getRight${pkColumn.methodName}());
			Assert.assertEquals(parent${entity.name}.getLeft${pkColumn.methodName}() + 1, parentChild${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(parent${entity.name}.getRight${pkColumn.methodName}() - 1, parentChild${entity.name}.getRight${pkColumn.methodName}());
		}

		@Test
		public void testMoveTreeIntoTreeFromRight() throws Exception {
			long ${scopeColumn.name} = ServiceTestUtil.nextLong();

			${entity.name} root${entity.name} = add${entity.name}(${scopeColumn.name}, null);

			${entity.name} leftRootChild${entity.name} = add${entity.name}(${scopeColumn.name}, root${entity.name}.get${pkColumn.methodName}());

			root${entity.name} = _persistence.fetchByPrimaryKey(root${entity.name}.getPrimaryKey());

			${entity.name} rightRootChild${entity.name} = add${entity.name}(${scopeColumn.name}, root${entity.name}.get${pkColumn.methodName}());

			root${entity.name} = _persistence.fetchByPrimaryKey(root${entity.name}.getPrimaryKey());

			long previousRootLeft${pkColumn.methodName} = root${entity.name}.getLeft${pkColumn.methodName}();
			long previousRootRight${pkColumn.methodName} = root${entity.name}.getRight${pkColumn.methodName}();

			${entity.name} parent${entity.name} = add${entity.name}(${scopeColumn.name}, null);

			${entity.name} parentChild${entity.name} = add${entity.name}(${scopeColumn.name}, parent${entity.name}.get${pkColumn.methodName}());

			parent${entity.name} = _persistence.fetchByPrimaryKey(parent${entity.name}.getPrimaryKey());

			parent${entity.name}.setParent${pkColumn.methodName}(leftRootChild${entity.name}.get${pkColumn.methodName}());

			_persistence.update(parent${entity.name});

			root${entity.name} = _persistence.fetchByPrimaryKey(root${entity.name}.getPrimaryKey());
			leftRootChild${entity.name} = _persistence.fetchByPrimaryKey(leftRootChild${entity.name}.getPrimaryKey());
			rightRootChild${entity.name} = _persistence.fetchByPrimaryKey(rightRootChild${entity.name}.getPrimaryKey());
			parentChild${entity.name} = _persistence.fetchByPrimaryKey(parentChild${entity.name}.getPrimaryKey());

			Assert.assertEquals(previousRootLeft${pkColumn.methodName}, root${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(previousRootRight${pkColumn.methodName} + 4, root${entity.name}.getRight${pkColumn.methodName}());
			Assert.assertEquals(root${entity.name}.getLeft${pkColumn.methodName}() + 1, leftRootChild${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(root${entity.name}.getRight${pkColumn.methodName}() - 3, leftRootChild${entity.name}.getRight${pkColumn.methodName}());
			Assert.assertEquals(root${entity.name}.getLeft${pkColumn.methodName}() + 7, rightRootChild${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(root${entity.name}.getRight${pkColumn.methodName}() - 1, rightRootChild${entity.name}.getRight${pkColumn.methodName}());
			Assert.assertEquals(leftRootChild${entity.name}.getLeft${pkColumn.methodName}() + 1, parent${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(leftRootChild${entity.name}.getRight${pkColumn.methodName}() - 1, parent${entity.name}.getRight${pkColumn.methodName}());
			Assert.assertEquals(parent${entity.name}.getLeft${pkColumn.methodName}() + 1, parentChild${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(parent${entity.name}.getRight${pkColumn.methodName}() - 1, parentChild${entity.name}.getRight${pkColumn.methodName}());
		}

		protected ${entity.name} add${entity.name}(long ${scopeColumn.name}, Long parent${pkColumn.methodName}) throws Exception {
			<#if entity.hasCompoundPK()>
				${entity.PKClassName} pk = new ${entity.PKClassName}(

				<#list entity.PKList as column>
					<#if column.type == "int">
						ServiceTestUtil.nextInt()
					<#elseif column.type == "long">
						ServiceTestUtil.nextLong()
					<#elseif column.type == "String">
						ServiceTestUtil.randomString()
					</#if>

					<#if column_has_next>
						,
					</#if>
				</#list>

				);
			<#else>
				<#assign column = entity.PKList[0]>

				${column.type} pk =

				<#if column.type == "int">
					ServiceTestUtil.nextInt()
				<#elseif column.type == "long">
					ServiceTestUtil.nextLong()
				<#elseif column.type == "String">
					ServiceTestUtil.randomString()
				</#if>

				;
			</#if>

			${entity.name} ${entity.varName} = _persistence.create(pk);

			<#list entity.regularColList as column>
				<#if !column.primary && ((parentPKColumn == "") || (parentPKColumn.name != column.name))>
					<#if column.name ="${scopeColumn.name}">
						${entity.varName}.set${column.methodName}(${scopeColumn.name});
					<#else>
						<#if column.type == "Blob">
							String ${column.name}String = ServiceTestUtil.randomString();

							byte[] ${column.name}Bytes = ${column.name}String.getBytes(StringPool.UTF8);

							Blob ${column.name}Blob = new OutputBlob(new UnsyncByteArrayInputStream(${column.name}Bytes), ${column.name}Bytes.length);
						</#if>

						${entity.varName}.set${column.methodName}(

						<#if column.type == "boolean">
							ServiceTestUtil.randomBoolean()
						<#elseif column.type == "double">
							ServiceTestUtil.nextDouble()
						<#elseif column.type == "int">
							ServiceTestUtil.nextInt()
						<#elseif column.type == "long">
							ServiceTestUtil.nextLong()
						<#elseif column.type == "Blob">
							${column.name}Blob
						<#elseif column.type == "Date">
							ServiceTestUtil.nextDate()
						<#elseif column.type == "String">
							ServiceTestUtil.randomString()
						</#if>

						);
					</#if>
				</#if>
			</#list>

			if (parent${pkColumn.methodName} != null) {
				${entity.varName}.setParent${pkColumn.methodName}(parent${pkColumn.methodName});
			}

			_persistence.update(${entity.varName});

			return ${entity.varName};
		}
	</#if>

	private static Log _log = LogFactoryUtil.getLog(${entity.name}PersistenceTest.class);

	private ${entity.name}Persistence _persistence = (${entity.name}Persistence)${beanLocatorUtilShortName}.locate(${entity.name}Persistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)${beanLocatorUtilShortName}.locate(TransactionalPersistenceAdvice.class.getName());

}
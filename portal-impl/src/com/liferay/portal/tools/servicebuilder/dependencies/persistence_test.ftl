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
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import java.sql.Blob;

import java.util.Arrays;
import java.util.List;

public class ${entity.name}PersistenceTest extends BasePersistenceTestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (${entity.name}Persistence)${beanLocatorUtilShortName}.locate(${entity.name}Persistence.class.getName());
	}

	public void testCreate() throws Exception {
		<#if entity.hasCompoundPK()>
			${entity.PKClassName} pk = new ${entity.PKClassName}(

			<#list entity.PKList as column>
				<#if column.type == "int">
					nextInt()
				<#elseif column.type == "long">
					nextLong()
				<#elseif column.type == "String">
					randomString()
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
				nextInt()
			<#elseif column.type == "long">
				nextLong()
			<#elseif column.type == "String">
				randomString()
			</#if>

			;
		</#if>

		${entity.name} ${entity.varName} = _persistence.create(pk);

		assertNotNull(${entity.varName});

		assertEquals(${entity.varName}.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		${entity.name} new${entity.name} = add${entity.name}();

		_persistence.remove(new${entity.name});

		${entity.name} existing${entity.name} = _persistence.fetchByPrimaryKey(new${entity.name}.getPrimaryKey());

		assertNull(existing${entity.name});
	}

	public void testUpdateNew() throws Exception {
		add${entity.name}();
	}

	public void testUpdateExisting() throws Exception {
		<#if entity.hasCompoundPK()>
			${entity.PKClassName} pk = new ${entity.PKClassName}(

			<#list entity.PKList as column>
				<#if column.type == "int">
					nextInt()
				<#elseif column.type == "long">
					nextLong()
				<#elseif column.type == "String">
					randomString()
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
				nextInt()
			<#elseif column.type == "long">
				nextLong()
			<#elseif column.type == "String">
				randomString()
			</#if>

			;
		</#if>

		${entity.name} new${entity.name} = _persistence.create(pk);

		<#list entity.regularColList as column>
			<#if !column.primary && ((parentPKColumn == "") || (parentPKColumn.name != column.name))>
				<#if column.type == "Blob">
					String new${column.methodName}String = randomString();

					byte[] new${column.methodName}Bytes = new${column.methodName}String.getBytes(StringPool.UTF8);

					Blob new${column.methodName}Blob = new OutputBlob(new UnsyncByteArrayInputStream(new${column.methodName}Bytes), new${column.methodName}Bytes.length);
				</#if>

				new${entity.name}.set${column.methodName}(

				<#if column.type == "boolean">
					randomBoolean()
				<#elseif column.type == "double">
					nextDouble()
				<#elseif column.type == "int">
					nextInt()
				<#elseif column.type == "long">
					nextLong()
				<#elseif column.type == "Date">
					nextDate()
				<#elseif column.type == "Blob">
					 new${column.methodName}Blob
				<#elseif column.type == "String">
					randomString()
				</#if>

				);
			</#if>
		</#list>

		_persistence.update(new${entity.name}, false);

		${entity.name} existing${entity.name} = _persistence.findByPrimaryKey(new${entity.name}.getPrimaryKey());

		<#list entity.regularColList as column>
			<#if column.type == "Blob">
				Blob existing${column.methodName} = existing${entity.name}.get${column.methodName}();

				assertTrue(Arrays.equals(existing${column.methodName}.getBytes(1, (int)existing${column.methodName}.length()), new${column.methodName}Bytes));
			<#elseif column.type == "Date">
				assertEquals(Time.getShortTimestamp(existing${entity.name}.get${column.methodName}()), Time.getShortTimestamp(new${entity.name}.get${column.methodName}()));
			<#else>
				assertEquals(existing${entity.name}.get${column.methodName}(), new${entity.name}.get${column.methodName}());
			</#if>
		</#list>
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		${entity.name} new${entity.name} = add${entity.name}();

		${entity.name} existing${entity.name} = _persistence.findByPrimaryKey(new${entity.name}.getPrimaryKey());

		assertEquals(existing${entity.name}, new${entity.name});
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		<#if entity.hasCompoundPK()>
			${entity.PKClassName} pk = new ${entity.PKClassName}(

			<#list entity.PKList as column>
				<#if column.type == "int">
					nextInt()
				<#elseif column.type == "long">
					nextLong()
				<#elseif column.type == "String">
					randomString()
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
				nextInt()
			<#elseif column.type == "long">
				nextLong()
			<#elseif column.type == "String">
				randomString()
			</#if>

			;
		</#if>

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw ${noSuchEntity}Exception");
		}
		catch (${noSuchEntity}Exception nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		${entity.name} new${entity.name} = add${entity.name}();

		${entity.name} existing${entity.name} = _persistence.fetchByPrimaryKey(new${entity.name}.getPrimaryKey());

		assertEquals(existing${entity.name}, new${entity.name});
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		<#if entity.hasCompoundPK()>
			${entity.PKClassName} pk = new ${entity.PKClassName}(

			<#list entity.PKList as column>
				<#if column.type == "int">
					nextInt()
				<#elseif column.type == "long">
					nextLong()
				<#elseif column.type == "String">
					randomString()
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
				nextInt()
			<#elseif column.type == "long">
				nextLong()
			<#elseif column.type == "String">
				randomString()
			</#if>

			;
		</#if>

		${entity.name} missing${entity.name} = _persistence.fetchByPrimaryKey(pk);

		assertNull(missing${entity.name});
	}

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

		assertEquals(1, result.size());

		${entity.name} existing${entity.name} = result.get(0);

		assertEquals(existing${entity.name}, new${entity.name});
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(${entity.name}.class, ${entity.name}.class.getClassLoader());

		<#if entity.hasCompoundPK()>
			<#list entity.PKList as column>
				dynamicQuery.add(RestrictionsFactoryUtil.eq("id.${column.name}",

				<#if column.type == "int">
					nextInt()
				<#elseif column.type == "long">
					nextLong()
				<#elseif column.type == "String">
					randomString()
				</#if>

				));
			</#list>
		<#else>
			<#assign column = entity.PKList[0]>

			dynamicQuery.add(RestrictionsFactoryUtil.eq("${column.name}",

			<#if column.type == "int">
				nextInt()
			<#elseif column.type == "long">
				nextLong()
			<#elseif column.type == "String">
				randomString()
			</#if>

			));
		</#if>

		List<${entity.name}> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

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

		assertEquals(1, result.size());

		Object existing${column.methodName} = result.get(0);

		assertEquals(existing${column.methodName}, new${column.methodName});
	}

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
			nextInt()
		<#elseif column.type == "long">
			nextLong()
		<#elseif column.type == "String">
			randomString()
		</#if>

		}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	<#assign uniqueFinderList = entity.getUniqueFinderList()>

	<#if uniqueFinderList?size != 0>
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
					<#if finderCol.isPrimitiveType()>
						assertEquals(existing${entity.name}ModelImpl.get${finderCol.methodName}(), existing${entity.name}ModelImpl.getOriginal${finderCol.methodName}());
					<#else>
						assertTrue(Validator.equals(existing${entity.name}ModelImpl.get${finderCol.methodName}(), existing${entity.name}ModelImpl.getOriginal${finderCol.methodName}()));
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
					nextInt()
				<#elseif column.type == "long">
					nextLong()
				<#elseif column.type == "String">
					randomString()
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
				nextInt()
			<#elseif column.type == "long">
				nextLong()
			<#elseif column.type == "String">
				randomString()
			</#if>

			;
		</#if>

		${entity.name} ${entity.varName} = _persistence.create(pk);

		<#list entity.regularColList as column>
			<#if !column.primary && ((parentPKColumn == "") || (parentPKColumn.name != column.name))>
				<#if column.type == "Blob">
					String ${column.name}String = randomString();

					byte[] ${column.name}Bytes = ${column.name}String.getBytes(StringPool.UTF8);

					Blob ${column.name}Blob = new OutputBlob(new UnsyncByteArrayInputStream(${column.name}Bytes), ${column.name}Bytes.length);
				</#if>

				${entity.varName}.set${column.methodName}(

				<#if column.type == "boolean">
					randomBoolean()
				<#elseif column.type == "double">
					nextDouble()
				<#elseif column.type == "int">
					nextInt()
				<#elseif column.type == "long">
					nextLong()
				<#elseif column.type == "Blob">
					${column.name}Blob
				<#elseif column.type == "Date">
					nextDate()
				<#elseif column.type == "String">
					randomString()
				</#if>

				);
			</#if>
		</#list>

		_persistence.update(${entity.varName}, false);

		return ${entity.varName};
	}

	<#if entity.isHierarchicalTree()>
		public void testMoveTree() throws Exception {
			long ${scopeColumn.name} = nextLong();

			${entity.name} root${entity.name} = add${entity.name}(${scopeColumn.name}, null);

			long previousRootLeft${pkColumn.methodName} = root${entity.name}.getLeft${pkColumn.methodName}();
			long previousRootRight${pkColumn.methodName} = root${entity.name}.getRight${pkColumn.methodName}();

			${entity.name} child${entity.name} = add${entity.name}(${scopeColumn.name}, root${entity.name}.get${pkColumn.methodName}());

			root${entity.name} = _persistence.fetchByPrimaryKey(root${entity.name}.getPrimaryKey());

			assertEquals(previousRootLeft${pkColumn.methodName}, root${entity.name}.getLeft${pkColumn.methodName}());
			assertEquals(previousRootRight${pkColumn.methodName} + 2, root${entity.name}.getRight${pkColumn.methodName}());
			assertEquals(root${entity.name}.getLeft${pkColumn.methodName}() + 1, child${entity.name}.getLeft${pkColumn.methodName}());
			assertEquals(root${entity.name}.getRight${pkColumn.methodName}() - 1, child${entity.name}.getRight${pkColumn.methodName}());
		}

		public void testMoveTreeFromLeft() throws Exception {
			long ${scopeColumn.name} = nextLong();

			${entity.name} parent${entity.name} = add${entity.name}(${scopeColumn.name}, null);

			${entity.name} child${entity.name} = add${entity.name}(${scopeColumn.name}, parent${entity.name}.get${pkColumn.methodName}());

			parent${entity.name} = _persistence.fetchByPrimaryKey(parent${entity.name}.getPrimaryKey());

			${entity.name} root${entity.name} = add${entity.name}(${scopeColumn.name}, null);

			long previousRootLeft${pkColumn.methodName} = root${entity.name}.getLeft${pkColumn.methodName}();
			long previousRootRight${pkColumn.methodName} = root${entity.name}.getRight${pkColumn.methodName}();

			parent${entity.name}.setParent${pkColumn.methodName}(root${entity.name}.get${pkColumn.methodName}());

			_persistence.update(parent${entity.name}, false);

			root${entity.name} = _persistence.fetchByPrimaryKey(root${entity.name}.getPrimaryKey());
			child${entity.name} = _persistence.fetchByPrimaryKey(child${entity.name}.getPrimaryKey());

			assertEquals(previousRootLeft${pkColumn.methodName} - 4, root${entity.name}.getLeft${pkColumn.methodName}());
			assertEquals(previousRootRight${pkColumn.methodName}, root${entity.name}.getRight${pkColumn.methodName}());
			assertEquals(root${entity.name}.getLeft${pkColumn.methodName}() + 1, parent${entity.name}.getLeft${pkColumn.methodName}());
			assertEquals(root${entity.name}.getRight${pkColumn.methodName}() - 1, parent${entity.name}.getRight${pkColumn.methodName}());
			assertEquals(parent${entity.name}.getLeft${pkColumn.methodName}() + 1, child${entity.name}.getLeft${pkColumn.methodName}());
			assertEquals(parent${entity.name}.getRight${pkColumn.methodName}() - 1, child${entity.name}.getRight${pkColumn.methodName}());
		}

		public void testMoveTreeFromRight() throws Exception {
			long ${scopeColumn.name} = nextLong();

			${entity.name} root${entity.name} = add${entity.name}(${scopeColumn.name}, null);

			long previousRootLeft${pkColumn.methodName} = root${entity.name}.getLeft${pkColumn.methodName}();
			long previousRootRight${pkColumn.methodName} = root${entity.name}.getRight${pkColumn.methodName}();

			${entity.name} parent${entity.name} = add${entity.name}(${scopeColumn.name}, null);

			${entity.name} child${entity.name} = add${entity.name}(${scopeColumn.name}, parent${entity.name}.get${pkColumn.methodName}());

			parent${entity.name} = _persistence.fetchByPrimaryKey(parent${entity.name}.getPrimaryKey());

			parent${entity.name}.setParent${pkColumn.methodName}(root${entity.name}.get${pkColumn.methodName}());

			_persistence.update(parent${entity.name}, false);

			root${entity.name} = _persistence.fetchByPrimaryKey(root${entity.name}.getPrimaryKey());
			child${entity.name} = _persistence.fetchByPrimaryKey(child${entity.name}.getPrimaryKey());

			assertEquals(previousRootLeft${pkColumn.methodName}, root${entity.name}.getLeft${pkColumn.methodName}());
			assertEquals(previousRootRight${pkColumn.methodName} + 4, root${entity.name}.getRight${pkColumn.methodName}());
			assertEquals(root${entity.name}.getLeft${pkColumn.methodName}() + 1, parent${entity.name}.getLeft${pkColumn.methodName}());
			assertEquals(root${entity.name}.getRight${pkColumn.methodName}() - 1, parent${entity.name}.getRight${pkColumn.methodName}());
			assertEquals(parent${entity.name}.getLeft${pkColumn.methodName}() + 1, child${entity.name}.getLeft${pkColumn.methodName}());
			assertEquals(parent${entity.name}.getRight${pkColumn.methodName}() - 1, child${entity.name}.getRight${pkColumn.methodName}());
		}

		public void testMoveTreeIntoTreeFromLeft() throws Exception {
			long ${scopeColumn.name} = nextLong();

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

			_persistence.update(parent${entity.name}, false);

			root${entity.name} = _persistence.fetchByPrimaryKey(root${entity.name}.getPrimaryKey());
			leftRootChild${entity.name} = _persistence.fetchByPrimaryKey(leftRootChild${entity.name}.getPrimaryKey());
			rightRootChild${entity.name} = _persistence.fetchByPrimaryKey(rightRootChild${entity.name}.getPrimaryKey());
			parentChild${entity.name} = _persistence.fetchByPrimaryKey(parentChild${entity.name}.getPrimaryKey());

			assertEquals(previousRootLeft${pkColumn.methodName} - 4, root${entity.name}.getLeft${pkColumn.methodName}());
			assertEquals(previousRootRight${pkColumn.methodName}, root${entity.name}.getRight${pkColumn.methodName}());
			assertEquals(root${entity.name}.getLeft${pkColumn.methodName}() + 1, leftRootChild${entity.name}.getLeft${pkColumn.methodName}());
			assertEquals(root${entity.name}.getRight${pkColumn.methodName}() - 7, leftRootChild${entity.name}.getRight${pkColumn.methodName}());
			assertEquals(root${entity.name}.getLeft${pkColumn.methodName}() + 3, rightRootChild${entity.name}.getLeft${pkColumn.methodName}());
			assertEquals(root${entity.name}.getRight${pkColumn.methodName}() - 1, rightRootChild${entity.name}.getRight${pkColumn.methodName}());
			assertEquals(rightRootChild${entity.name}.getLeft${pkColumn.methodName}() + 1, parent${entity.name}.getLeft${pkColumn.methodName}());
			assertEquals(rightRootChild${entity.name}.getRight${pkColumn.methodName}() - 1, parent${entity.name}.getRight${pkColumn.methodName}());
			assertEquals(parent${entity.name}.getLeft${pkColumn.methodName}() + 1, parentChild${entity.name}.getLeft${pkColumn.methodName}());
			assertEquals(parent${entity.name}.getRight${pkColumn.methodName}() - 1, parentChild${entity.name}.getRight${pkColumn.methodName}());
		}

		public void testMoveTreeIntoTreeFromRight() throws Exception {
			long ${scopeColumn.name} = nextLong();

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

			_persistence.update(parent${entity.name}, false);

			root${entity.name} = _persistence.fetchByPrimaryKey(root${entity.name}.getPrimaryKey());
			leftRootChild${entity.name} = _persistence.fetchByPrimaryKey(leftRootChild${entity.name}.getPrimaryKey());
			rightRootChild${entity.name} = _persistence.fetchByPrimaryKey(rightRootChild${entity.name}.getPrimaryKey());
			parentChild${entity.name} = _persistence.fetchByPrimaryKey(parentChild${entity.name}.getPrimaryKey());

			assertEquals(previousRootLeft${pkColumn.methodName}, root${entity.name}.getLeft${pkColumn.methodName}());
			assertEquals(previousRootRight${pkColumn.methodName} + 4, root${entity.name}.getRight${pkColumn.methodName}());
			assertEquals(root${entity.name}.getLeft${pkColumn.methodName}() + 1, leftRootChild${entity.name}.getLeft${pkColumn.methodName}());
			assertEquals(root${entity.name}.getRight${pkColumn.methodName}() - 3, leftRootChild${entity.name}.getRight${pkColumn.methodName}());
			assertEquals(root${entity.name}.getLeft${pkColumn.methodName}() + 7, rightRootChild${entity.name}.getLeft${pkColumn.methodName}());
			assertEquals(root${entity.name}.getRight${pkColumn.methodName}() - 1, rightRootChild${entity.name}.getRight${pkColumn.methodName}());
			assertEquals(leftRootChild${entity.name}.getLeft${pkColumn.methodName}() + 1, parent${entity.name}.getLeft${pkColumn.methodName}());
			assertEquals(leftRootChild${entity.name}.getRight${pkColumn.methodName}() - 1, parent${entity.name}.getRight${pkColumn.methodName}());
			assertEquals(parent${entity.name}.getLeft${pkColumn.methodName}() + 1, parentChild${entity.name}.getLeft${pkColumn.methodName}());
			assertEquals(parent${entity.name}.getRight${pkColumn.methodName}() - 1, parentChild${entity.name}.getRight${pkColumn.methodName}());
		}

		protected ${entity.name} add${entity.name}(long ${scopeColumn.name}, Long parent${pkColumn.methodName}) throws Exception {
			<#if entity.hasCompoundPK()>
				${entity.PKClassName} pk = new ${entity.PKClassName}(

				<#list entity.PKList as column>
					<#if column.type == "int">
						nextInt()
					<#elseif column.type == "long">
						nextLong()
					<#elseif column.type == "String">
						randomString()
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
					nextInt()
				<#elseif column.type == "long">
					nextLong()
				<#elseif column.type == "String">
					randomString()
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
							String ${column.name}String = randomString();

							byte[] ${column.name}Bytes = ${column.name}String.getBytes(StringPool.UTF8);

							Blob ${column.name}Blob = new OutputBlob(new UnsyncByteArrayInputStream(${column.name}Bytes), ${column.name}Bytes.length);
						</#if>

						${entity.varName}.set${column.methodName}(

						<#if column.type == "boolean">
							randomBoolean()
						<#elseif column.type == "double">
							nextDouble()
						<#elseif column.type == "int">
							nextInt()
						<#elseif column.type == "long">
							nextLong()
						<#elseif column.type == "Blob">
							${column.name}Blob
						<#elseif column.type == "Date">
							nextDate()
						<#elseif column.type == "String">
							randomString()
						</#if>

						);
					</#if>
				</#if>
			</#list>

			if (parent${pkColumn.methodName} != null) {
				${entity.varName}.setParent${pkColumn.methodName}(parent${pkColumn.methodName});
			}

			_persistence.update(${entity.varName}, false);

			return ${entity.varName};
		}
	</#if>

	private ${entity.name}Persistence _persistence;

}
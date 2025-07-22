/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.report.internal.empty.model;

import com.liferay.exportimport.kernel.empty.model.EmptyModelManager;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.report.service.ExportImportReportEntryLocalService;
import com.liferay.petra.function.UnsafeBiFunction;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.lazy.referencing.LazyReferencingThreadLocal;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.function.BiFunction;
import java.util.function.Supplier;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Carlos Correa
 */
public class EmptyModelManagerImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			_emptyModelManager, "_classNameLocalService",
			_classNameLocalService);
		ReflectionTestUtil.setFieldValue(
			_emptyModelManager, "_exportImportReportEntryLocalService",
			_exportImportReportEntryLocalService);
		ReflectionTestUtil.setFieldValue(
			_emptyModelManager, "_groupLocalService", _groupLocalService);
	}

	@After
	public void tearDown() {
		Mockito.verifyNoMoreInteractions(
			_classNameLocalService, _exportImportReportEntryLocalService,
			_group, _groupLocalService, _user);
	}

	@Test
	public void testGetOrAddEmptyModelCompanyScopedWithDisabledLazyReferencingAndReturningItem()
		throws Exception {

		try (SafeCloseable safeCloseable =
				LazyReferencingThreadLocal.setEnabledWithSafeCloseable(false)) {

			Assert.assertSame(
				_user,
				_emptyModelManager.getOrAddEmptyModel(
					User.class, RandomTestUtil.randomLong(),
					() -> {
						Assert.fail();

						return null;
					},
					RandomTestUtil.randomString(),
					_toBiFunction(
						() -> {
							Assert.fail();

							return null;
						}),
					_toUnsafeBiFunction(() -> _user)));
		}
	}

	@Test
	public void testGetOrAddEmptyModelCompanyScopedWithDisabledLazyReferencingAndThrowingException()
		throws Exception {

		try (SafeCloseable safeCloseable =
				LazyReferencingThreadLocal.setEnabledWithSafeCloseable(false)) {

			Assert.assertThrows(
				NoSuchUserException.class,
				() -> _emptyModelManager.getOrAddEmptyModel(
					User.class, RandomTestUtil.randomLong(),
					() -> {
						Assert.fail();

						return null;
					},
					RandomTestUtil.randomString(),
					_toBiFunction(
						() -> {
							Assert.fail();

							return null;
						}),
					_toUnsafeBiFunction(
						() -> {
							throw new NoSuchUserException();
						})));
		}
	}

	@Test
	public void testGetOrAddEmptyModelCompanyScopedWithEnabledLazyReferencingAndAddingEmptyItem()
		throws Exception {

		try (SafeCloseable safeCloseable =
				LazyReferencingThreadLocal.setEnabledWithSafeCloseable(true)) {

			long classNameId = RandomTestUtil.randomLong();

			Mockito.when(
				_classNameLocalService.getClassNameId(User.class.getName())
			).thenReturn(
				classNameId
			);

			long exportImportConfigurationId = RandomTestUtil.randomLong();

			ExportImportThreadLocal.setExportImportConfigurationId(
				exportImportConfigurationId);

			long companyId = RandomTestUtil.randomLong();
			String externalReferenceCode = RandomTestUtil.randomString();

			Assert.assertSame(
				_user,
				_emptyModelManager.getOrAddEmptyModel(
					User.class, companyId, () -> _user, externalReferenceCode,
					_toBiFunction(() -> null),
					_toUnsafeBiFunction(
						() -> {
							Assert.fail();

							return null;
						})));

			Mockito.verify(
				_classNameLocalService
			).getClassNameId(
				User.class.getName()
			);

			Mockito.verify(
				_exportImportReportEntryLocalService
			).addEmptyExportImportReportEntry(
				0L, companyId, externalReferenceCode, classNameId,
				exportImportConfigurationId
			);
		}
	}

	@Test
	public void testGetOrAddEmptyModelCompanyScopedWithEnabledLazyReferencingAndReturningItem()
		throws Exception {

		try (SafeCloseable safeCloseable =
				LazyReferencingThreadLocal.setEnabledWithSafeCloseable(true)) {

			Assert.assertSame(
				_user,
				_emptyModelManager.getOrAddEmptyModel(
					User.class,
					() -> {
						Assert.fail();

						return null;
					},
					RandomTestUtil.randomString(), _toBiFunction(() -> _user),
					_toUnsafeBiFunction(
						() -> {
							Assert.fail();

							return null;
						}),
					RandomTestUtil.randomLong()));
		}
	}

	@Test
	public void testGetOrAddEmptyModelGroupScopedWithDisabledLazyReferencingAndReturningItem()
		throws Exception {

		try (SafeCloseable safeCloseable =
				LazyReferencingThreadLocal.setEnabledWithSafeCloseable(false)) {

			Assert.assertSame(
				_user,
				_emptyModelManager.getOrAddEmptyModel(
					User.class,
					() -> {
						Assert.fail();

						return null;
					},
					RandomTestUtil.randomString(),
					_toBiFunction(
						() -> {
							Assert.fail();

							return null;
						}),
					_toUnsafeBiFunction(() -> _user),
					RandomTestUtil.randomLong()));
		}
	}

	@Test
	public void testGetOrAddEmptyModelGroupScopedWithDisabledLazyReferencingAndThrowingException()
		throws Exception {

		try (SafeCloseable safeCloseable =
				LazyReferencingThreadLocal.setEnabledWithSafeCloseable(false)) {

			Assert.assertThrows(
				NoSuchUserException.class,
				() -> _emptyModelManager.getOrAddEmptyModel(
					User.class,
					() -> {
						Assert.fail();

						return null;
					},
					RandomTestUtil.randomString(),
					_toBiFunction(
						() -> {
							Assert.fail();

							return null;
						}),
					_toUnsafeBiFunction(
						() -> {
							throw new NoSuchUserException();
						}),
					RandomTestUtil.randomLong()));
		}
	}

	@Test
	public void testGetOrAddEmptyModelGroupScopedWithEnabledLazyReferencingAndAddingEmptyItem()
		throws Exception {

		try (SafeCloseable safeCloseable =
				LazyReferencingThreadLocal.setEnabledWithSafeCloseable(true)) {

			long classNameId = RandomTestUtil.randomLong();

			Mockito.when(
				_classNameLocalService.getClassNameId(User.class.getName())
			).thenReturn(
				classNameId
			);

			long companyId = RandomTestUtil.randomLong();

			Mockito.when(
				_group.getCompanyId()
			).thenReturn(
				companyId
			);

			long groupId = RandomTestUtil.randomLong();

			Mockito.when(
				_groupLocalService.fetchGroup(groupId)
			).thenReturn(
				_group
			);

			long exportImportConfigurationId = RandomTestUtil.randomLong();

			ExportImportThreadLocal.setExportImportConfigurationId(
				exportImportConfigurationId);

			String externalReferenceCode = RandomTestUtil.randomString();

			Assert.assertSame(
				_user,
				_emptyModelManager.getOrAddEmptyModel(
					User.class, () -> _user, externalReferenceCode,
					_toBiFunction(() -> null),
					_toUnsafeBiFunction(
						() -> {
							Assert.fail();

							return null;
						}),
					groupId));

			Mockito.verify(
				_classNameLocalService
			).getClassNameId(
				User.class.getName()
			);

			Mockito.verify(
				_group
			).getCompanyId();

			Mockito.verify(
				_groupLocalService
			).fetchGroup(
				groupId
			);

			Mockito.verify(
				_exportImportReportEntryLocalService
			).addEmptyExportImportReportEntry(
				groupId, companyId, externalReferenceCode, classNameId,
				exportImportConfigurationId
			);
		}
	}

	@Test
	public void testGetOrAddEmptyModelGroupScopedWithEnabledLazyReferencingAndReturningItem()
		throws Exception {

		try (SafeCloseable safeCloseable =
				LazyReferencingThreadLocal.setEnabledWithSafeCloseable(true)) {

			long companyId = RandomTestUtil.randomLong();

			Mockito.when(
				_group.getCompanyId()
			).thenReturn(
				companyId
			);

			long groupId = RandomTestUtil.randomLong();

			Mockito.when(
				_groupLocalService.fetchGroup(groupId)
			).thenReturn(
				_group
			);

			Assert.assertSame(
				_user,
				_emptyModelManager.getOrAddEmptyModel(
					User.class,
					() -> {
						Assert.fail();

						return null;
					},
					RandomTestUtil.randomString(), _toBiFunction(() -> _user),
					_toUnsafeBiFunction(
						() -> {
							Assert.fail();

							return null;
						}),
					groupId));
		}
	}

	@Test
	public void testIsEmptyModel() {
		Assert.assertFalse(_emptyModelManager.isEmptyModel());
	}

	@Test
	public void testIsEmptyModelWhenAddingEmptyModel() throws Exception {
		Assert.assertFalse(_emptyModelManager.isEmptyModel());

		try (SafeCloseable safeCloseable =
				LazyReferencingThreadLocal.setEnabledWithSafeCloseable(true)) {

			long classNameId = RandomTestUtil.randomLong();

			Mockito.when(
				_classNameLocalService.getClassNameId(User.class.getName())
			).thenReturn(
				classNameId
			);

			long exportImportConfigurationId = RandomTestUtil.randomLong();

			ExportImportThreadLocal.setExportImportConfigurationId(
				exportImportConfigurationId);

			long companyId = RandomTestUtil.randomLong();
			String externalReferenceCode = RandomTestUtil.randomString();

			_emptyModelManager.getOrAddEmptyModel(
				User.class, companyId,
				() -> {
					Assert.assertTrue(_emptyModelManager.isEmptyModel());

					return _user;
				},
				externalReferenceCode, _toBiFunction(() -> null),
				_toUnsafeBiFunction(
					() -> {
						Assert.fail();

						return null;
					}));

			Mockito.verify(
				_classNameLocalService
			).getClassNameId(
				User.class.getName()
			);

			Mockito.verify(
				_exportImportReportEntryLocalService
			).addEmptyExportImportReportEntry(
				0L, companyId, externalReferenceCode, classNameId,
				exportImportConfigurationId
			);
		}
	}

	private BiFunction<String, Long, User> _toBiFunction(
		Supplier<User> supplier) {

		return (externalReferenceCode, companyId) -> supplier.get();
	}

	private UnsafeBiFunction<String, Long, User, Exception> _toUnsafeBiFunction(
		UnsafeSupplier<User, Exception> unsafeSupplier) {

		return (externalReferenceCode, companyId) -> unsafeSupplier.get();
	}

	private final ClassNameLocalService _classNameLocalService = Mockito.mock(
		ClassNameLocalService.class);
	private final EmptyModelManager _emptyModelManager =
		new EmptyModelManagerImpl();
	private final ExportImportReportEntryLocalService
		_exportImportReportEntryLocalService = Mockito.mock(
			ExportImportReportEntryLocalService.class);
	private final Group _group = Mockito.mock(Group.class);
	private final GroupLocalService _groupLocalService = Mockito.mock(
		GroupLocalService.class);
	private final User _user = Mockito.mock(User.class);

}
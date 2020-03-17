package ${configYAML.apiPackagePath}.internal.resource.${escapedVersion}.factory;

import ${configYAML.apiPackagePath}.resource.${escapedVersion}.${schemaName}Resource;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Component(immediate = true, service = ${schemaName}Resource.Factory.class)
@Generated("")
public class ${schemaName}ResourceFactoryImpl implements ${schemaName}Resource.Factory {

	@Override
	public ${schemaName}Resource.Builder create() {
		return new ${schemaName}Resource.Builder() {

			@Override
			public ${schemaName}Resource build() {
				if (_user == null) {
					throw new IllegalArgumentException("User is not set");
				}

				return (${schemaName}Resource)ProxyUtil.newProxyInstance(${schemaName}Resource.class.getClassLoader(), new Class<?>[] {${schemaName}Resource.class}, (proxy, method, arguments) -> _invoke(method, arguments, _checkPermissions, _user));
			}

			@Override
			public ${schemaName}Resource.Builder checkPermissions(boolean checkPermissions) {
				_checkPermissions = checkPermissions;

				return this;
			}

			@Override
			public ${schemaName}Resource.Builder user(User user) {
				_user = user;

				return this;
			}

			private boolean _checkPermissions = true;
			private User _user;

		};
	}

	@Activate
	protected void activate() {
		${schemaName}Resource.FactoryHolder.factory = this;
	}

	@Deactivate
	protected void deactivate() {
		${schemaName}Resource.FactoryHolder.factory = null;
	}

	private Object _invoke(Method method, Object[] arguments, boolean checkPermissions, User user) throws Throwable {
		String name = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(user.getUserId());

		PermissionChecker permissionChecker = PermissionThreadLocal.getPermissionChecker();

		if (checkPermissions) {
			PermissionThreadLocal.setPermissionChecker(_defaultPermissionCheckerFactory.create(user));
		}
		else {
			PermissionThreadLocal.setPermissionChecker(_liberalPermissionCheckerFactory.create(user));
		}

		${schemaName}Resource ${schemaVarName}Resource = _componentServiceObjects.getService();

		Company company = _companyLocalService.getCompany(user.getCompanyId());

		${schemaVarName}Resource.setContextCompany(company);

		${schemaVarName}Resource.setContextUser(user);

		try {
			return method.invoke(${schemaVarName}Resource, arguments);
		}
		catch (InvocationTargetException invocationTargetException) {
			throw invocationTargetException.getTargetException();
		}
		finally {
			_componentServiceObjects.ungetService(${schemaVarName}Resource);

			PrincipalThreadLocal.setName(name);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		}
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<${schemaName}Resource> _componentServiceObjects;

	@Reference
	private PermissionCheckerFactory _defaultPermissionCheckerFactory;

	@Reference(target = "(permission.checker.type=liberal)")
	private PermissionCheckerFactory _liberalPermissionCheckerFactory;

	@Reference
	private UserLocalService _userLocalService;

}
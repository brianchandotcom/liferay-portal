package ${packagePath}.uad.test;

import ${apiPackagePath}.model.${entity.name};

import org.junit.Assume;

import org.osgi.service.component.annotations.Component;

/**
 * @author ${author}
 */
@Component(
	immediate = true,
	service = ${entity.name}UADEntityTestHelper.class
)
public class ${entity.name}UADEntityTestHelper {

	/**
	 * Implement add${entity.name}() to enable some UAD tests.
	 *
	 * <p>
	 * Several UAD tests depend on creating one or more valid ${entity.names} with a specified userId in order to execute correctly. Implement add${entity.name}() such that it creates a valid ${entity.name} with the specified userId value and returns it in order to enable the UAD tests that depend on it.
	 * </p>
	 *
	 */
	public ${entity.name} add${entity.name}(long userId) throws Exception {
		Assume.assumeTrue(false);

		return null;
	}

	<#if entity.hasEntityColumn("statusByUserId")>
		/**
		 * Implement add${entity.name}WithStatusByUserId() to enable some UAD tests.
		 *
		 * <p>
		 * Several UAD tests depend on creating one or more valid ${entity.names} with specified userId and statusByUserId in order to execute correctly. Implement add${entity.name}WithStatusByUserId() such that it creates a valid ${entity.name} with the specified userId and statusByUserId values and returns it in order to enable the UAD tests that depend on it.
		 * </p>
		 *
		 */
		public ${entity.name} add${entity.name}WithStatusByUserId(long userId, long statusByUserId) throws Exception {
			Assume.assumeTrue(false);

			return null;
		}
	</#if>

}
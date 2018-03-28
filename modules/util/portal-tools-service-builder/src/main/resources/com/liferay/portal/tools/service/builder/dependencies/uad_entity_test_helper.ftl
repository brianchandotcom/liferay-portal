package ${packagePath}.uad.test;

import ${apiPackagePath}.model.${entity.name};

import java.util.List;

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
	 * Several UAD tests depend on creating one or more valid ${entity.names} with a specified user ID in order to execute correctly. Implement add${entity.name}() such that it creates a valid ${entity.name} with the specified user ID value and returns it in order to enable the UAD tests that depend on it.
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
		 * Several UAD tests depend on creating one or more valid ${entity.names} with specified user ID and status by user ID in order to execute correctly. Implement add${entity.name}WithStatusByUserId() such that it creates a valid ${entity.name} with the specified user ID and status by user ID values and returns it in order to enable the UAD tests that depend on it.
		 * </p>
		 *
		 */
		public ${entity.name} add${entity.name}WithStatusByUserId(long userId, long statusByUserId) throws Exception {
			Assume.assumeTrue(false);

			return null;
		}
	</#if>

	/**
	 * Implement cleanUpDependencies(List<${entity.name}> ${entity.varNames}) if tests require additional tear down logic.
	 *
	 * <p>
	 * Several UAD tests depend on creating one or more valid ${entity.names} with specified user ID and status by user ID in order to execute correctly. Implement cleanUpDependencies(List<${entity.name}> ${entity.varNames}) such that any additional objects created during the construction of ${entity.varNames} are safely removed.
	 * </p>
	 *
	 */
	public void cleanUpDependencies(List<${entity.name}> ${entity.varNames}) throws Exception {
	}

}
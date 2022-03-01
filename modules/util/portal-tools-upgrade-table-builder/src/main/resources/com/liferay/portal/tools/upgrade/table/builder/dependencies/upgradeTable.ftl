${copyright}

package ${package};

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
* @author Brian Wing Shun Chan
* @generated
*/
public class ${className} {

	/*
	* NOTE FOR DEVELOPERS:
	*
	* Never modify this class directly. Use buildUpgradeTable command to generate it.
	*/
	public static UpgradeProcess create() {
		return new UpgradeProcess() {

			@Override
			protected void doUpgrade() throws Exception {
				if (!hasTable(TABLE_NAME)) {
					runSQL(TABLE_SQL_CREATE);
				}
			}
		};
	}

	private static final String TABLE_NAME =${tableName};

	private static final String TABLE_SQL_CREATE=${tableSQLCreate};

}
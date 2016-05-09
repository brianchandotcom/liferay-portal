/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.sync.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.sync.service.SyncDLObjectServiceUtil;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class SyncDLObjectServiceClpInvoker {
	public SyncDLObjectServiceClpInvoker() {
		_methodName86 = "getOSGiServiceIdentifier";

		_methodParameterTypes86 = new String[] {  };

		_methodName91 = "addFileEntry";

		_methodParameterTypes91 = new String[] {
				"long", "long", "java.lang.String", "java.lang.String",
				"java.lang.String", "java.lang.String", "java.lang.String",
				"java.io.File", "java.lang.String",
				"com.liferay.portal.kernel.service.ServiceContext"
			};

		_methodName92 = "addFolder";

		_methodParameterTypes92 = new String[] {
				"long", "long", "java.lang.String", "java.lang.String",
				"com.liferay.portal.kernel.service.ServiceContext"
			};

		_methodName93 = "cancelCheckOut";

		_methodParameterTypes93 = new String[] { "long" };

		_methodName94 = "checkInFileEntry";

		_methodParameterTypes94 = new String[] {
				"long", "boolean", "java.lang.String",
				"com.liferay.portal.kernel.service.ServiceContext"
			};

		_methodName95 = "checkOutFileEntry";

		_methodParameterTypes95 = new String[] {
				"long", "com.liferay.portal.kernel.service.ServiceContext"
			};

		_methodName96 = "checkOutFileEntry";

		_methodParameterTypes96 = new String[] {
				"long", "java.lang.String", "long",
				"com.liferay.portal.kernel.service.ServiceContext"
			};

		_methodName97 = "copyFileEntry";

		_methodParameterTypes97 = new String[] {
				"long", "long", "long", "java.lang.String", "java.lang.String",
				"com.liferay.portal.kernel.service.ServiceContext"
			};

		_methodName98 = "getAllFolderSyncDLObjects";

		_methodParameterTypes98 = new String[] { "long" };

		_methodName99 = "getFileEntrySyncDLObject";

		_methodParameterTypes99 = new String[] {
				"long", "long", "java.lang.String"
			};

		_methodName100 = "getFileEntrySyncDLObjects";

		_methodParameterTypes100 = new String[] { "long", "long" };

		_methodName101 = "getFolderSyncDLObject";

		_methodParameterTypes101 = new String[] { "long" };

		_methodName102 = "getFolderSyncDLObject";

		_methodParameterTypes102 = new String[] {
				"long", "long", "java.lang.String"
			};

		_methodName103 = "getFolderSyncDLObjects";

		_methodParameterTypes103 = new String[] { "long", "long" };

		_methodName104 = "getGroup";

		_methodParameterTypes104 = new String[] { "long" };

		_methodName105 = "getLatestModifiedTime";

		_methodParameterTypes105 = new String[] {  };

		_methodName106 = "getSyncContext";

		_methodParameterTypes106 = new String[] {  };

		_methodName107 = "getSyncDLObjectUpdate";

		_methodParameterTypes107 = new String[] { "long", "long", "int" };

		_methodName108 = "getSyncDLObjectUpdate";

		_methodParameterTypes108 = new String[] { "long", "long", "int", "boolean" };

		_methodName109 = "getSyncDLObjectUpdate";

		_methodParameterTypes109 = new String[] { "long", "long", "long" };

		_methodName110 = "getUserSitesGroups";

		_methodParameterTypes110 = new String[] {  };

		_methodName111 = "moveFileEntry";

		_methodParameterTypes111 = new String[] {
				"long", "long",
				"com.liferay.portal.kernel.service.ServiceContext"
			};

		_methodName112 = "moveFileEntryToTrash";

		_methodParameterTypes112 = new String[] { "long" };

		_methodName113 = "moveFolder";

		_methodParameterTypes113 = new String[] {
				"long", "long",
				"com.liferay.portal.kernel.service.ServiceContext"
			};

		_methodName114 = "moveFolderToTrash";

		_methodParameterTypes114 = new String[] { "long" };

		_methodName115 = "patchFileEntry";

		_methodParameterTypes115 = new String[] {
				"long", "long", "java.lang.String", "java.lang.String",
				"java.lang.String", "java.lang.String", "java.lang.String",
				"boolean", "java.io.File", "java.lang.String",
				"com.liferay.portal.kernel.service.ServiceContext"
			};

		_methodName116 = "restoreFileEntryFromTrash";

		_methodParameterTypes116 = new String[] { "long" };

		_methodName117 = "restoreFolderFromTrash";

		_methodParameterTypes117 = new String[] { "long" };

		_methodName118 = "updateFileEntries";

		_methodParameterTypes118 = new String[] { "java.io.File" };

		_methodName119 = "updateFileEntry";

		_methodParameterTypes119 = new String[] {
				"long", "java.lang.String", "java.lang.String",
				"java.lang.String", "java.lang.String", "java.lang.String",
				"boolean", "java.io.File", "java.lang.String",
				"com.liferay.portal.kernel.service.ServiceContext"
			};

		_methodName120 = "updateFolder";

		_methodParameterTypes120 = new String[] {
				"long", "java.lang.String", "java.lang.String",
				"com.liferay.portal.kernel.service.ServiceContext"
			};
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName86.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes86, parameterTypes)) {
			return SyncDLObjectServiceUtil.getOSGiServiceIdentifier();
		}

		if (_methodName91.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes91, parameterTypes)) {
			return SyncDLObjectServiceUtil.addFileEntry(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				(java.lang.String)arguments[2], (java.lang.String)arguments[3],
				(java.lang.String)arguments[4], (java.lang.String)arguments[5],
				(java.lang.String)arguments[6], (java.io.File)arguments[7],
				(java.lang.String)arguments[8],
				(com.liferay.portal.kernel.service.ServiceContext)arguments[9]);
		}

		if (_methodName92.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes92, parameterTypes)) {
			return SyncDLObjectServiceUtil.addFolder(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				(java.lang.String)arguments[2], (java.lang.String)arguments[3],
				(com.liferay.portal.kernel.service.ServiceContext)arguments[4]);
		}

		if (_methodName93.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes93, parameterTypes)) {
			return SyncDLObjectServiceUtil.cancelCheckOut(((Long)arguments[0]).longValue());
		}

		if (_methodName94.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes94, parameterTypes)) {
			return SyncDLObjectServiceUtil.checkInFileEntry(((Long)arguments[0]).longValue(),
				((Boolean)arguments[1]).booleanValue(),
				(java.lang.String)arguments[2],
				(com.liferay.portal.kernel.service.ServiceContext)arguments[3]);
		}

		if (_methodName95.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes95, parameterTypes)) {
			return SyncDLObjectServiceUtil.checkOutFileEntry(((Long)arguments[0]).longValue(),
				(com.liferay.portal.kernel.service.ServiceContext)arguments[1]);
		}

		if (_methodName96.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes96, parameterTypes)) {
			return SyncDLObjectServiceUtil.checkOutFileEntry(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1],
				((Long)arguments[2]).longValue(),
				(com.liferay.portal.kernel.service.ServiceContext)arguments[3]);
		}

		if (_methodName97.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes97, parameterTypes)) {
			return SyncDLObjectServiceUtil.copyFileEntry(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				((Long)arguments[2]).longValue(),
				(java.lang.String)arguments[3], (java.lang.String)arguments[4],
				(com.liferay.portal.kernel.service.ServiceContext)arguments[5]);
		}

		if (_methodName98.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes98, parameterTypes)) {
			return SyncDLObjectServiceUtil.getAllFolderSyncDLObjects(((Long)arguments[0]).longValue());
		}

		if (_methodName99.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes99, parameterTypes)) {
			return SyncDLObjectServiceUtil.getFileEntrySyncDLObject(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(), (java.lang.String)arguments[2]);
		}

		if (_methodName100.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes100, parameterTypes)) {
			return SyncDLObjectServiceUtil.getFileEntrySyncDLObjects(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName101.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes101, parameterTypes)) {
			return SyncDLObjectServiceUtil.getFolderSyncDLObject(((Long)arguments[0]).longValue());
		}

		if (_methodName102.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes102, parameterTypes)) {
			return SyncDLObjectServiceUtil.getFolderSyncDLObject(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(), (java.lang.String)arguments[2]);
		}

		if (_methodName103.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes103, parameterTypes)) {
			return SyncDLObjectServiceUtil.getFolderSyncDLObjects(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName104.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes104, parameterTypes)) {
			return SyncDLObjectServiceUtil.getGroup(((Long)arguments[0]).longValue());
		}

		if (_methodName105.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes105, parameterTypes)) {
			return SyncDLObjectServiceUtil.getLatestModifiedTime();
		}

		if (_methodName106.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes106, parameterTypes)) {
			return SyncDLObjectServiceUtil.getSyncContext();
		}

		if (_methodName107.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes107, parameterTypes)) {
			return SyncDLObjectServiceUtil.getSyncDLObjectUpdate(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName108.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes108, parameterTypes)) {
			return SyncDLObjectServiceUtil.getSyncDLObjectUpdate(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				((Integer)arguments[2]).intValue(),
				((Boolean)arguments[3]).booleanValue());
		}

		if (_methodName109.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes109, parameterTypes)) {
			return SyncDLObjectServiceUtil.getSyncDLObjectUpdate(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				((Long)arguments[2]).longValue());
		}

		if (_methodName110.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes110, parameterTypes)) {
			return SyncDLObjectServiceUtil.getUserSitesGroups();
		}

		if (_methodName111.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes111, parameterTypes)) {
			return SyncDLObjectServiceUtil.moveFileEntry(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				(com.liferay.portal.kernel.service.ServiceContext)arguments[2]);
		}

		if (_methodName112.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes112, parameterTypes)) {
			return SyncDLObjectServiceUtil.moveFileEntryToTrash(((Long)arguments[0]).longValue());
		}

		if (_methodName113.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes113, parameterTypes)) {
			return SyncDLObjectServiceUtil.moveFolder(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				(com.liferay.portal.kernel.service.ServiceContext)arguments[2]);
		}

		if (_methodName114.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes114, parameterTypes)) {
			return SyncDLObjectServiceUtil.moveFolderToTrash(((Long)arguments[0]).longValue());
		}

		if (_methodName115.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes115, parameterTypes)) {
			return SyncDLObjectServiceUtil.patchFileEntry(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				(java.lang.String)arguments[2], (java.lang.String)arguments[3],
				(java.lang.String)arguments[4], (java.lang.String)arguments[5],
				(java.lang.String)arguments[6],
				((Boolean)arguments[7]).booleanValue(),
				(java.io.File)arguments[8], (java.lang.String)arguments[9],
				(com.liferay.portal.kernel.service.ServiceContext)arguments[10]);
		}

		if (_methodName116.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes116, parameterTypes)) {
			return SyncDLObjectServiceUtil.restoreFileEntryFromTrash(((Long)arguments[0]).longValue());
		}

		if (_methodName117.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes117, parameterTypes)) {
			return SyncDLObjectServiceUtil.restoreFolderFromTrash(((Long)arguments[0]).longValue());
		}

		if (_methodName118.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes118, parameterTypes)) {
			return SyncDLObjectServiceUtil.updateFileEntries((java.io.File)arguments[0]);
		}

		if (_methodName119.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes119, parameterTypes)) {
			return SyncDLObjectServiceUtil.updateFileEntry(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], (java.lang.String)arguments[2],
				(java.lang.String)arguments[3], (java.lang.String)arguments[4],
				(java.lang.String)arguments[5],
				((Boolean)arguments[6]).booleanValue(),
				(java.io.File)arguments[7], (java.lang.String)arguments[8],
				(com.liferay.portal.kernel.service.ServiceContext)arguments[9]);
		}

		if (_methodName120.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes120, parameterTypes)) {
			return SyncDLObjectServiceUtil.updateFolder(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], (java.lang.String)arguments[2],
				(com.liferay.portal.kernel.service.ServiceContext)arguments[3]);
		}

		throw new UnsupportedOperationException();
	}

	private String _methodName86;
	private String[] _methodParameterTypes86;
	private String _methodName91;
	private String[] _methodParameterTypes91;
	private String _methodName92;
	private String[] _methodParameterTypes92;
	private String _methodName93;
	private String[] _methodParameterTypes93;
	private String _methodName94;
	private String[] _methodParameterTypes94;
	private String _methodName95;
	private String[] _methodParameterTypes95;
	private String _methodName96;
	private String[] _methodParameterTypes96;
	private String _methodName97;
	private String[] _methodParameterTypes97;
	private String _methodName98;
	private String[] _methodParameterTypes98;
	private String _methodName99;
	private String[] _methodParameterTypes99;
	private String _methodName100;
	private String[] _methodParameterTypes100;
	private String _methodName101;
	private String[] _methodParameterTypes101;
	private String _methodName102;
	private String[] _methodParameterTypes102;
	private String _methodName103;
	private String[] _methodParameterTypes103;
	private String _methodName104;
	private String[] _methodParameterTypes104;
	private String _methodName105;
	private String[] _methodParameterTypes105;
	private String _methodName106;
	private String[] _methodParameterTypes106;
	private String _methodName107;
	private String[] _methodParameterTypes107;
	private String _methodName108;
	private String[] _methodParameterTypes108;
	private String _methodName109;
	private String[] _methodParameterTypes109;
	private String _methodName110;
	private String[] _methodParameterTypes110;
	private String _methodName111;
	private String[] _methodParameterTypes111;
	private String _methodName112;
	private String[] _methodParameterTypes112;
	private String _methodName113;
	private String[] _methodParameterTypes113;
	private String _methodName114;
	private String[] _methodParameterTypes114;
	private String _methodName115;
	private String[] _methodParameterTypes115;
	private String _methodName116;
	private String[] _methodParameterTypes116;
	private String _methodName117;
	private String[] _methodParameterTypes117;
	private String _methodName118;
	private String[] _methodParameterTypes118;
	private String _methodName119;
	private String[] _methodParameterTypes119;
	private String _methodName120;
	private String[] _methodParameterTypes120;
}
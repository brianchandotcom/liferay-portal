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

package com.liferay.portal.repository.capabilities;

import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.repository.capabilities.SyncCapability;
import com.liferay.portal.kernel.repository.event.RepositoryEventAware;
import com.liferay.portal.kernel.repository.event.RepositoryEventListener;
import com.liferay.portal.kernel.repository.event.RepositoryEventType;
import com.liferay.portal.kernel.repository.event.TrashRepositoryEventType;
import com.liferay.portal.kernel.repository.event.WorkflowRepositoryEventType;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.registry.RepositoryEventRegistry;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.repository.capabilities.util.GroupServiceAdapter;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portlet.documentlibrary.model.DLSyncConstants;
import com.liferay.portlet.documentlibrary.model.DLSyncEvent;
import com.liferay.portlet.documentlibrary.service.DLSyncEventLocalService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Adolfo Pérez
 */
public class LiferaySyncCapability
	implements RepositoryEventAware, SyncCapability {

	public LiferaySyncCapability(
		GroupServiceAdapter groupServiceAdapter,
		DLSyncEventLocalService dlSyncEventLocalService) {

		_groupServiceAdapter = groupServiceAdapter;
		_dlSyncEventLocalService = dlSyncEventLocalService;
	}

	@Override
	public void registerRepositoryEventListeners(
		RepositoryEventRegistry repositoryEventRegistry) {

		repositoryEventRegistry.registerRepositoryEventListener(
			RepositoryEventType.Add.class, Folder.class,
			_ADD_FOLDER_EVENT_LISTENER);
		repositoryEventRegistry.registerRepositoryEventListener(
			RepositoryEventType.Delete.class, FileEntry.class,
			_DELETE_FILE_ENTRY_EVENT_LISTENER);
		repositoryEventRegistry.registerRepositoryEventListener(
			RepositoryEventType.Delete.class, Folder.class,
			_DELETE_FOLDER_EVENT_LISTENER);
		repositoryEventRegistry.registerRepositoryEventListener(
			RepositoryEventType.Move.class, FileEntry.class,
			_MOVE_FILE_ENTRY_EVENT_LISTENER);
		repositoryEventRegistry.registerRepositoryEventListener(
			RepositoryEventType.Move.class, Folder.class,
			_MOVE_FOLDER_EVENT_LISTENER);
		repositoryEventRegistry.registerRepositoryEventListener(
			TrashRepositoryEventType.EntryRestored.class, FileEntry.class,
			_RESTORE_FILE_ENTRY_EVENT_LISTENER);
		repositoryEventRegistry.registerRepositoryEventListener(
			TrashRepositoryEventType.EntryRestored.class, Folder.class,
			_RESTORE_FOLDER_EVENT_LISTENER);
		repositoryEventRegistry.registerRepositoryEventListener(
			TrashRepositoryEventType.EntryTrashed.class, FileEntry.class,
			_TRASH_FILE_ENTRY_EVENT_LISTENER);
		repositoryEventRegistry.registerRepositoryEventListener(
			TrashRepositoryEventType.EntryTrashed.class, Folder.class,
			_TRASH_FOLDER_EVENT_LISTENER);
		repositoryEventRegistry.registerRepositoryEventListener(
			RepositoryEventType.Update.class, FileEntry.class,
			_UPDATE_FILE_ENTRY_EVENT_LISTENER);
		repositoryEventRegistry.registerRepositoryEventListener(
			RepositoryEventType.Update.class, Folder.class,
			_UPDATE_FOLDER_EVENT_LISTENER);
		repositoryEventRegistry.registerRepositoryEventListener(
			WorkflowRepositoryEventType.Add.class, FileEntry.class,
			_WORKFLOW_ADD_FILE_ENTRY_EVENT_LISTENER);
		repositoryEventRegistry.registerRepositoryEventListener(
			WorkflowRepositoryEventType.Update.class, FileEntry.class,
			_WORKFLOW_UPDATE_FILE_ENTRY_EVENT_LISTENER);
	}

	protected boolean isStagingGroup(long groupId) {
		try {
			Group group = _groupServiceAdapter.getGroup(groupId);

			return group.isStagingGroup();
		}
		catch (Exception e) {
			return false;
		}
	}

	protected void registerDLSyncEventCallback(
		String event, FileEntry fileEntry) {

		if (isStagingGroup(fileEntry.getGroupId()) ||
			!(fileEntry instanceof LiferayFileEntry)) {

			return;
		}

		registerDLSyncEventCallback(
			event, DLSyncConstants.TYPE_FILE, fileEntry.getFileEntryId());
	}

	protected void registerDLSyncEventCallback(String event, Folder folder) {
		if (isStagingGroup(folder.getGroupId()) ||
			!(folder instanceof LiferayFolder)) {

			return;
		}

		registerDLSyncEventCallback(
			event, DLSyncConstants.TYPE_FOLDER, folder.getFolderId());
	}

	protected void registerDLSyncEventCallback(
		final String event, final String type, final long typePK) {

		DLSyncEvent dlSyncEvent = _dlSyncEventLocalService.addDLSyncEvent(
			event, type, typePK);

		final long modifiedTime = dlSyncEvent.getModifiedTime();

		TransactionCommitCallbackUtil.registerCallback(
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					Message message = new Message();

					Map<String, Object> values = new HashMap<>(4);

					values.put("event", event);
					values.put("modifiedTime", modifiedTime);
					values.put("type", type);
					values.put("typePK", typePK);

					message.setValues(values);

					MessageBusUtil.sendMessage(
						DestinationNames.DOCUMENT_LIBRARY_SYNC_EVENT_PROCESSOR,
						message);

					return null;
				}

			}
		);
	}

	private final RepositoryEventListener
		<RepositoryEventType.Add, Folder> _ADD_FOLDER_EVENT_LISTENER =
			new SyncFolderRepositoryEventListener<>(DLSyncConstants.EVENT_ADD);

	private final RepositoryEventListener
		<RepositoryEventType.Delete, FileEntry>
			_DELETE_FILE_ENTRY_EVENT_LISTENER =
				new SyncFileEntryRepositoryEventListener<>(
					DLSyncConstants.EVENT_DELETE);

	private final RepositoryEventListener
		<RepositoryEventType.Delete, Folder> _DELETE_FOLDER_EVENT_LISTENER =
			new SyncFolderRepositoryEventListener<>(
				DLSyncConstants.EVENT_DELETE);

	private final RepositoryEventListener
		<RepositoryEventType.Move, FileEntry> _MOVE_FILE_ENTRY_EVENT_LISTENER =
			new SyncFileEntryRepositoryEventListener<>(
				DLSyncConstants.EVENT_MOVE);

	private final RepositoryEventListener
		<RepositoryEventType.Move, Folder> _MOVE_FOLDER_EVENT_LISTENER =
			new SyncFolderRepositoryEventListener<>(DLSyncConstants.EVENT_MOVE);

	private final RepositoryEventListener
		<TrashRepositoryEventType.EntryRestored, FileEntry>
			_RESTORE_FILE_ENTRY_EVENT_LISTENER =
				new SyncFileEntryRepositoryEventListener<>(
					DLSyncConstants.EVENT_RESTORE);

	private final RepositoryEventListener
		<TrashRepositoryEventType.EntryRestored, Folder>
			_RESTORE_FOLDER_EVENT_LISTENER =
				new SyncFolderRepositoryEventListener<>(
					DLSyncConstants.EVENT_RESTORE);

	private final RepositoryEventListener
		<TrashRepositoryEventType.EntryTrashed, FileEntry>
			_TRASH_FILE_ENTRY_EVENT_LISTENER =
				new SyncFileEntryRepositoryEventListener<>(
					DLSyncConstants.EVENT_TRASH);

	private final RepositoryEventListener
		<TrashRepositoryEventType.EntryTrashed, Folder>
			_TRASH_FOLDER_EVENT_LISTENER =
				new SyncFolderRepositoryEventListener<>(
					DLSyncConstants.EVENT_TRASH);

	private final RepositoryEventListener
		<RepositoryEventType.Update, FileEntry>
			_UPDATE_FILE_ENTRY_EVENT_LISTENER =
				new SyncFileEntryRepositoryEventListener<>(
					DLSyncConstants.EVENT_UPDATE);

	private final RepositoryEventListener
		<RepositoryEventType.Update, Folder> _UPDATE_FOLDER_EVENT_LISTENER =
			new SyncFolderRepositoryEventListener<>(
				DLSyncConstants.EVENT_UPDATE);

	private final RepositoryEventListener
		<WorkflowRepositoryEventType.Add, FileEntry>
			_WORKFLOW_ADD_FILE_ENTRY_EVENT_LISTENER =
				new SyncFileEntryRepositoryEventListener<>(
					DLSyncConstants.EVENT_ADD);

	private final RepositoryEventListener
		<WorkflowRepositoryEventType.Update, FileEntry>
			_WORKFLOW_UPDATE_FILE_ENTRY_EVENT_LISTENER =
				new SyncFileEntryRepositoryEventListener<>(
					DLSyncConstants.EVENT_UPDATE);

	private final DLSyncEventLocalService _dlSyncEventLocalService;
	private final GroupServiceAdapter _groupServiceAdapter;

	private class SyncFileEntryRepositoryEventListener
			<S extends RepositoryEventType>
		implements RepositoryEventListener<S, FileEntry> {

		public SyncFileEntryRepositoryEventListener(String syncEvent) {
			_syncEvent = syncEvent;
		}

		@Override
		public void execute(FileEntry fileEntry) {
			registerDLSyncEventCallback(_syncEvent, fileEntry);
		}

		private final String _syncEvent;

	}

	private class SyncFolderRepositoryEventListener
			<S extends RepositoryEventType>
		implements RepositoryEventListener<S, Folder> {

		public SyncFolderRepositoryEventListener(String syncEvent) {
			_syncEvent = syncEvent;
		}

		@Override
		public void execute(Folder folder) {
			registerDLSyncEventCallback(_syncEvent, folder);
		}

		private final String _syncEvent;

	}

}
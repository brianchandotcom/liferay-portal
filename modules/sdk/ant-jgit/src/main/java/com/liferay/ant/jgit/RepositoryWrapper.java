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

package com.liferay.ant.jgit;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.events.ListenerList;
import org.eclipse.jgit.events.RepositoryEvent;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.BaseRepositoryBuilder;
import org.eclipse.jgit.lib.ObjectDatabase;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.RebaseTodoLine;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.RefRename;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.ReflogReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryState;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.util.FS;

/**
 * @author Shuyang Zhou
 */
public class RepositoryWrapper extends Repository {

	public RepositoryWrapper(Repository repository) {
		super(new BaseRepositoryBuilder<>());

		this.repository = repository;
	}

	@Override
	public void close() {
		repository.close();
	}

	@Override
	public void create() throws IOException {
		repository.create();
	}

	@Override
	public void create(boolean bare) throws IOException {
		repository.create(bare);
	}

	@Override
	public void fireEvent(RepositoryEvent<?> repositoryEvent) {
		repository.fireEvent(repositoryEvent);
	}

	@Override
	public Set<ObjectId> getAdditionalHaves() {
		return repository.getAdditionalHaves();
	}

	@Override
	public Map<String, Ref> getAllRefs() {
		return repository.getAllRefs();
	}

	@Override
	public Map<AnyObjectId, Set<Ref>> getAllRefsByPeeledObjectId() {
		return repository.getAllRefsByPeeledObjectId();
	}

	@Override
	public String getBranch() throws IOException {
		return repository.getBranch();
	}

	@Override
	public StoredConfig getConfig() {
		return repository.getConfig();
	}

	@Override
	public File getDirectory() {
		return repository.getDirectory();
	}

	@Override
	public FS getFS() {
		return repository.getFS();
	}

	@Override
	public String getFullBranch() throws IOException {
		return repository.getFullBranch();
	}

	@Override
	public File getIndexFile() throws NoWorkTreeException {
		return repository.getIndexFile();
	}

	@Override
	public ListenerList getListenerList() {
		return repository.getListenerList();
	}

	@Override
	public ObjectDatabase getObjectDatabase() {
		return repository.getObjectDatabase();
	}

	@Override
	public Ref getRef(String name) throws IOException {
		return repository.getRef(name);
	}

	@Override
	public RefDatabase getRefDatabase() {
		return repository.getRefDatabase();
	}

	@Override
	public ReflogReader getReflogReader(String refName) throws IOException {
		return repository.getReflogReader(refName);
	}

	@Override
	public String getRemoteName(String refName) {
		return repository.getRemoteName(refName);
	}

	@Override
	public Set<String> getRemoteNames() {
		return repository.getRemoteNames();
	}

	@Override
	public RepositoryState getRepositoryState() {
		return repository.getRepositoryState();
	}

	@Override
	public Map<String, Ref> getTags() {
		return repository.getTags();
	}

	@Override
	public File getWorkTree() throws NoWorkTreeException {
		return repository.getWorkTree();
	}

	@Override
	public boolean hasObject(AnyObjectId anyObjectId) {
		return repository.hasObject(anyObjectId);
	}

	@Override
	public void incrementOpen() {
		repository.incrementOpen();
	}

	@Override
	public boolean isBare() {
		return repository.isBare();
	}

	@Override
	public DirCache lockDirCache()
		throws CorruptObjectException, IOException, NoWorkTreeException {

		return repository.lockDirCache();
	}

	@Override
	public ObjectInserter newObjectInserter() {
		return repository.newObjectInserter();
	}

	@Override
	public ObjectReader newObjectReader() {
		return repository.newObjectReader();
	}

	@Override
	public void notifyIndexChanged() {
		repository.notifyIndexChanged();
	}

	@Override
	public ObjectLoader open(AnyObjectId anyObjectId)
		throws IOException, MissingObjectException {

		return repository.open(anyObjectId);
	}

	@Override
	public ObjectLoader open(AnyObjectId anyObjectId, int typeHint)
		throws IncorrectObjectTypeException,
		IOException, MissingObjectException {

		return repository.open(anyObjectId, typeHint);
	}

	@Override
	public Ref peel(Ref ref) {
		return repository.peel(ref);
	}

	@Override
	public ObjectId readCherryPickHead()
		throws IOException, NoWorkTreeException {

		return repository.readCherryPickHead();
	}

	@Override
	public String readCommitEditMsg() throws IOException, NoWorkTreeException {
		return repository.readCommitEditMsg();
	}

	@Override
	public DirCache readDirCache()
		throws CorruptObjectException, IOException, NoWorkTreeException {

		return repository.readDirCache();
	}

	@Override
	public String readMergeCommitMsg() throws IOException, NoWorkTreeException {
		return repository.readMergeCommitMsg();
	}

	@Override
	public List<ObjectId> readMergeHeads()
		throws IOException, NoWorkTreeException {

		return repository.readMergeHeads();
	}

	@Override
	public ObjectId readOrigHead() throws IOException, NoWorkTreeException {
		return repository.readOrigHead();
	}

	@Override
	public List<RebaseTodoLine> readRebaseTodo(
			String path, boolean includeComments)
		throws IOException {

		return repository.readRebaseTodo(path, includeComments);
	}

	@Override
	public ObjectId readRevertHead() throws IOException, NoWorkTreeException {
		return repository.readRevertHead();
	}

	@Override
	public String readSquashCommitMsg() throws IOException {
		return repository.readSquashCommitMsg();
	}

	@Override
	public RefRename renameRef(String fromRef, String toRef)
		throws IOException {

		return repository.renameRef(fromRef, toRef);
	}

	@Override
	public ObjectId resolve(String revName)
		throws AmbiguousObjectException,
		IncorrectObjectTypeException, IOException, RevisionSyntaxException {

		return repository.resolve(revName);
	}

	@Override
	public void scanForRepoChanges() throws IOException {
		repository.scanForRepoChanges();
	}

	@Override
	public String shortenRemoteBranchName(String refName) {
		return repository.shortenRemoteBranchName(refName);
	}

	@Override
	public String simplify(String revName)
		throws AmbiguousObjectException, IOException {

		return repository.simplify(revName);
	}

	@Override
	public String toString() {
		return repository.toString();
	}

	@Override
	public RefUpdate updateRef(String ref) throws IOException {
		return repository.updateRef(ref);
	}

	@Override
	public RefUpdate updateRef(String ref, boolean detach) throws IOException {
		return repository.updateRef(ref, detach);
	}

	@Override
	public void writeCherryPickHead(ObjectId headObjectId) throws IOException {
		repository.writeCherryPickHead(headObjectId);
	}

	@Override
	public void writeCommitEditMsg(String message) throws IOException {
		repository.writeCommitEditMsg(message);
	}

	@Override
	public void writeMergeCommitMsg(String message) throws IOException {
		repository.writeMergeCommitMsg(message);
	}

	@Override
	public void writeMergeHeads(List<? extends ObjectId> headsObjectIds)
		throws IOException {

		repository.writeMergeHeads(headsObjectIds);
	}

	@Override
	public void writeOrigHead(ObjectId headObjectId) throws IOException {
		repository.writeOrigHead(headObjectId);
	}

	@Override
	public void writeRebaseTodoFile(
			String path, List<RebaseTodoLine> steps, boolean append)
		throws IOException {

		repository.writeRebaseTodoFile(path, steps, append);
	}

	@Override
	public void writeRevertHead(ObjectId headObjectId) throws IOException {
		repository.writeRevertHead(headObjectId);
	}

	@Override
	public void writeSquashCommitMsg(String message) throws IOException {
		repository.writeSquashCommitMsg(message);
	}

	protected final Repository repository;

}
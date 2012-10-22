/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.engines.mediawiki;

import java.sql.Connection;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jamwiki.DataHandler;
import org.jamwiki.model.Category;
import org.jamwiki.model.Interwiki;
import org.jamwiki.model.LogItem;
import org.jamwiki.model.Namespace;
import org.jamwiki.model.RecentChange;
import org.jamwiki.model.Role;
import org.jamwiki.model.RoleMap;
import org.jamwiki.model.Topic;
import org.jamwiki.model.TopicType;
import org.jamwiki.model.TopicVersion;
import org.jamwiki.model.VirtualWiki;
import org.jamwiki.model.Watchlist;
import org.jamwiki.model.WikiFile;
import org.jamwiki.model.WikiFileVersion;
import org.jamwiki.model.WikiGroup;
import org.jamwiki.model.WikiUser;
import org.jamwiki.utils.Pagination;

/**
 * @author Jonathan Potter
 */
public class DummyDataHandler implements DataHandler {

	public boolean authenticate(String username, String password) {
		return false;
	}

	public boolean canMoveTopic(Topic fromTopic, String destination) {
		return false;
	}

	public void deleteInterwiki(Interwiki interwiki) {
	}

	public void deleteTopic(Topic topic, TopicVersion topicVersion) {
	}

	public void executeUpgradeQuery(String prop, Connection conn) {
	}

	public void executeUpgradeUpdate(String prop, Connection conn) {
	}

	public List<Category> getAllCategories(
		String virtualWiki, Pagination pagination) {

		return null;
	}

	public List<Role> getAllRoles() {
		return null;
	}

	public List<String> getAllTopicNames(
		String virtualWiki, boolean includeDeleted) {

		return null;
	}

	public List<WikiFileVersion> getAllWikiFileVersions(
		String virtualWiki, String topicName, boolean descending) {

		return null;
	}

	public List<LogItem> getLogItems(
		String virtualWiki, int logType, Pagination pagination,
		boolean descending) {

		return null;
	}

	public List<RecentChange> getRecentChanges(
		String virtualWiki, Pagination pagination, boolean descending) {

		return null;
	}

	public List<RoleMap> getRoleMapByLogin(String loginFragment) {
		return null;
	}

	public List<RoleMap> getRoleMapByRole(String roleName) {
		return null;
	}

	public List<Role> getRoleMapGroup(String groupName) {
		return null;
	}

	public List<RoleMap> getRoleMapGroups() {
		return null;
	}

	public List<Role> getRoleMapUser(String login) {
		return null;
	}

	public List<RecentChange> getTopicHistory(
		String virtualWiki, String topicName, Pagination pagination,
		boolean descending) {

		return null;
	}

	public List<String> getTopicsAdmin(
		String virtualWiki, Pagination pagination) {

		return null;
	}

	public List<RecentChange> getUserContributions(
		String virtualWiki, String userString, Pagination pagination,
		boolean descending) {

		return null;
	}

	public List<VirtualWiki> getVirtualWikiList() {
		return null;
	}

	public Watchlist getWatchlist(String virtualWiki, int userId) {
		return null;
	}

	public List<RecentChange> getWatchlist(
		String virtualWiki, int userId, Pagination pagination) {

		return null;
	}

	public List<Category> lookupCategoryTopics(
		String virtualWiki, String categoryName) {

		return null;
	}

	public Map<String, String> lookupConfiguration() {
		return null;
	}

	public Interwiki lookupInterwiki(String interwikiPrefix) {
		return null;
	}

	public List<Interwiki> lookupInterwikis() {
		return null;
	}

	public Namespace lookupNamespace(
		String virtualWiki, String namespaceString) {

		return null;
	}

	public Namespace lookupNamespaceById(int namespaceId) {
		return null;
	}

	public List<Namespace> lookupNamespaces() {
		return null;
	}

	public Topic lookupTopic(
		String virtualWiki, String topicName, boolean deleteOK,
		Connection conn) {

		return null;
	}

	public Topic lookupTopicById(String virtualWiki, int topicId) {
		return null;
	}

	public Map<Integer, String> lookupTopicByType(
		String virtualWiki, TopicType topicType1, TopicType topicType2,
		Integer namespaceId, Pagination pagination) {

		return null;
	}

	public int lookupTopicCount(String virtualWiki, Integer namespaceId) {
		return 0;
	}

	public List<String> lookupTopicLinkOrphans(
		String virtualWiki, int namespaceId) {

		return null;
	}

	public List<String> lookupTopicLinks(String virtualWiki, String topicName) {
		return null;
	}

	public String lookupTopicName(String virtualWiki, String topicName) {
		return null;
	}

	public TopicVersion lookupTopicVersion(int topicVersionId) {
		return null;
	}

	public Integer lookupTopicVersionNextId(int topicVersionId) {
		return null;
	}

	public VirtualWiki lookupVirtualWiki(String virtualWikiName) {
		return null;
	}

	public WikiFile lookupWikiFile(String virtualWiki, String topicName) {
		return null;
	}

	public int lookupWikiFileCount(String virtualWiki) {
		return 0;
	}

	public WikiGroup lookupWikiGroup(String groupName) {
		return null;
	}

	public WikiUser lookupWikiUser(int userId) {
		return null;
	}

	public WikiUser lookupWikiUser(String username) {
		return null;
	}

	public int lookupWikiUserCount() {
		return 0;
	}

	public String lookupWikiUserEncryptedPassword(String username) {
		return null;
	}

	public List<String> lookupWikiUsers(Pagination pagination) {
		return null;
	}

	public void moveTopic(
		Topic fromTopic, TopicVersion fromVersion, String destination) {
	}

	public void orderTopicVersions(
		Topic topic, List<Integer> topicVersionIdList) {
	}

	public void reloadLogItems() {
	}

	public void reloadRecentChanges() {
	}

	public void setup(
		Locale locale, WikiUser user, String username,
		String encryptedPassword) {
	}

	public void setupSpecialPages(
		Locale locale, WikiUser user, VirtualWiki virtualWiki) {
	}

	public void undeleteTopic(Topic topic, TopicVersion topicVersion) {
	}

	public void updateSpecialPage(
		Locale locale, String virtualWiki, String topicName,
		String userDisplay) {
	}

	public void writeConfiguration(Map<String, String> configuration) {
	}

	public void writeFile(WikiFile wikiFile, WikiFileVersion wikiFileVersion) {
	}

	public void writeInterwiki(Interwiki interwiki) {
	}

	public void writeNamespace(
		Namespace mainNamespace, Namespace commentsNamespace) {
	}

	public void writeNamespaceTranslations(
		List<Namespace> namespaces, String virtualWiki) {
	}

	public void writeRole(Role role, boolean update) {
	}

	public void writeRoleMapGroup(int groupId, List<String> roles) {
	}

	public void writeRoleMapUser(String username, List<String> roles) {
	}

	public void writeTopic(
		Topic topic, TopicVersion topicVersion,
		LinkedHashMap<String, String> categories, List<String> links) {
	}

	public void writeTopicVersion(Topic topic, TopicVersion topicVersion) {
	}

	public void writeVirtualWiki(VirtualWiki virtualWiki) {
	}

	public void writeWatchlistEntry(
		Watchlist watchlist, String virtualWiki, String topicName, int userId) {
	}

	public void writeWikiGroup(WikiGroup group) {
	}

	public void writeWikiUser(
		WikiUser user, String username, String encryptedPassword) {
	}

}
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

package com.liferay.data.engine.model;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.model.ClassedModel;

import java.io.Serializable;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

/**
 * This class represents a layout made of a collection of {@link DEDataLayoutPage}.
 * @review
 * @author Jeyvison Nascimento
 */
public class DEDataLayout implements ClassedModel, Serializable {

	/**
	 * Overrided equals method
	 * @param obj
	 * @return
	 * @review
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DEDataLayout)) {
			return false;
		}

		DEDataLayout deDataLayout = (DEDataLayout)obj;

		if (Objects.equals(
				_deDataDefinitionId, deDataLayout._deDataDefinitionId) &&
			Objects.equals(_deDataLayoutId, deDataLayout._deDataLayoutId) &&
			Arrays.equals(
				_deDataLayoutPages.toArray(),
				deDataLayout._deDataLayoutPages.toArray()) &&
			Objects.equals(
				_defaultLanguageId, deDataLayout._defaultLanguageId) &&
			Objects.equals(_description, deDataLayout._description) &&
			Objects.equals(_name, deDataLayout._name) &&
			Objects.equals(_paginationMode, deDataLayout._paginationMode)) {

			return true;
		}

		return false;
	}

	/**
	 * Returns the creation date of this layout.
	 * @review
	 * @return the creation date
	 */
	public Date getCreateDate() {
		return _createDate;
	}

	/**
	 * Gets the id of the data definition associated with this layout
	 * @return the data definition id
	 */
	public Long getDEDataDefinitionId() {
		return _deDataDefinitionId;
	}

	/**
	 * Return the id of the DEDataLayout.
	 * @return the id of the DEDataLayout object
	 * @review
	 */
	public Long getDEDataLayoutId() {
		return _deDataLayoutId;
	}

	/**
	 * Returns a queue of al the {@link DEDataLayoutPage } that belongs to
	 * this layout. Otherwise returns an empty queue.
	 * @return A queue of {@link DEDataLayoutPage}
	 * @review
	 */
	public Queue<DEDataLayoutPage> getDEDataLayoutPages() {
		return _deDataLayoutPages;
	}

	/**
	 * Returns the language id associates with this layout
	 * @return the language id (i.e: pt_BR)
	 * @review
	 */
	public String getDefaultLanguageId() {
		return _defaultLanguageId;
	}

	/**
	 * Returns the localized descriptions of the layout
	 * @return A map of localized descriptions.
	 * @review
	 */
	public Map<Locale, String> getDescription() {
		return _description;
	}

	/**
	 * Method needed when implementing {@link ClassedModel}.
	 * @throws UnsupportedOperationException Always thrown by this method
	 * @return Nothing. Always throws an exception
	 * @review
	 */
	@Override
	public ExpandoBridge getExpandoBridge() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Method needed when implementing {@link ClassedModel}.
	 * @return the DEDataLayout class
	 * @review
	 */
	@Override
	public Class<?> getModelClass() {
		return DEDataLayout.class;
	}

	/**
	 *  Method needed when implementing {@link ClassedModel}.
	 * @return The FQN of this class
	 * @review
	 */
	@Override
	public String getModelClassName() {
		return DEDataLayout.class.getName();
	}

	/**
	 * Returns the last modification date of this layout
	 * @return The modified date
	 * @review
	 */
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	/**
	 * Returns the localized names of the layout.
	 * @return a Map of names;
	 * @review
	 */
	public Map<Locale, String> getName() {
		return _name;
	}

	/**
	 * Returns the paginated mode that will be used by this Layout
	 * @return the paginated mode used by the layout
	 * @review
	 */
	public String getPaginationMode() {
		return _paginationMode;
	}

	/**
	 *  Method needed when implementing {@link ClassedModel}.
	 * @return The primary Key object(Long) of the DEDataLayout
	 * @review
	 */
	@Override
	public Serializable getPrimaryKeyObj() {
		return _deDataLayoutId;
	}

	/**
	 * Returns the user id responsible to create this data layout
	 * @return the user id of the author of the layout
	 * @review
	 */
	public Long getUserId() {
		return _userId;
	}

	/**
	 * Overrided hashCode method
	 * @return
	 * @review
	 */
	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _deDataDefinitionId);

		hash = HashUtil.hash(hash, _deDataLayoutId);
		hash = HashUtil.hash(hash, _deDataLayoutPages.hashCode());
		hash = HashUtil.hash(hash, _defaultLanguageId);
		hash = HashUtil.hash(hash, _description.hashCode());
		hash = HashUtil.hash(hash, _name.hashCode());

		return HashUtil.hash(hash, _paginationMode);
	}

	/**
	 * Sets the creation date of this layout.
	 * @review
	 * @param createDate the creation date
	 */
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	/**
	 * Sets the id o the data definition associated with this layout
	 * @param deDataDefinitionId the data definition id
	 * @review
	 */
	public void setDEDataDefinitionId(Long deDataDefinitionId) {
		_deDataDefinitionId = deDataDefinitionId;
	}

	/**
	 * The id of the DEDataLayout. If no id is passed it's assumed that a create
	 * operation will be performed. If the id is informed, an update operation
	 * will be executed
	 * @param deDataLayoutId the Id of the DEDataLayout
	 * @review
	 */
	public void setDEDataLayoutId(Long deDataLayoutId) {
		_deDataLayoutId = deDataLayoutId;
	}

	/**
	 * Sets a Queue of {@link DEDataLayoutPage} to the layout. if the parameter
	 * is null then a new queue is created.
	 * @param deDataLayoutPages a Queue of {@link DEDataLayoutPage}
	 * @review
	 */
	public void setDEDataLayoutPages(
		Queue<DEDataLayoutPage> deDataLayoutPages) {

		if (deDataLayoutPages == null) {
			deDataLayoutPages = new ArrayDeque<>();
		}

		_deDataLayoutPages = deDataLayoutPages;
	}

	/**
	 * Sets the default language id of this layout. A valid language id must be
	 * provided in order to save this DataLayout (i.e "pt_BR")
	 *
	 * @param defaultLanguageId the language id String
	 * @review
	 */
	public void setDefaultLanguageId(String defaultLanguageId) {
		_defaultLanguageId = defaultLanguageId;
	}

	/**
	 * Sets the localized layout descriptions ( i.e. "{pt_BR: layout description}").
	 * If the description parameter is null, a new Map is created.
	 * @param description a Map containing the Layout descriptions
	 * @review
	 */
	public void setDescription(Map<Locale, String> description) {
		_description = description;
	}

	/**
	 * Sets the modification date of this layout.
	 * @review
	 * @param modifiedDate
	 */
	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	/**
	 * Sets the localized name of the layout (i.e "{pt_BR:layout name}").
	 * If the name parameter is null, a new map is created.
	 * @param name A Map containing the localized names of the Layout
	 * @review
	 */
	public void setName(Map<Locale, String> name) {
		if (name == null) {
			name = new HashMap<>();
		}

		_name = name;
	}

	/**
	 * Sets the pagination mode to this layout. A valid pagination mode must be
	 * provided in order to save the DataLayout. It must be Wizard or
	 * @param paginationMode the pagination mode String.
	 * @review
	 */
	public void setPaginationMode(String paginationMode) {
		_paginationMode = paginationMode;
	}

	/**
	 *  Method needed when implementing {@link ClassedModel}.
	 * @param primaryKeyObj The primary Key object(Long) of the DEDataLayout
	 * @review
	 */
	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_deDataLayoutId = (Long)primaryKeyObj;
	}

	/**
	 * Sets the user id of the layout's author
	 * @param userId a Long parameter containing the user id
	 * @review
	 */
	public void setUserId(Long userId) {
		_userId = userId;
	}

	private Date _createDate;
	private Long _deDataDefinitionId;
	private Long _deDataLayoutId;
	private Queue<DEDataLayoutPage> _deDataLayoutPages = new ArrayDeque<>();
	private String _defaultLanguageId;
	private Map<Locale, String> _description = new HashMap<>();
	private Date _modifiedDate;
	private Map<Locale, String> _name = new HashMap<>();
	private String _paginationMode;
	private Long _userId;

}
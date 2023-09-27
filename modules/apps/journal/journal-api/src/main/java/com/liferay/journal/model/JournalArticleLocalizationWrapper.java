/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link JournalArticleLocalization}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticleLocalization
 * @generated
 */
public class JournalArticleLocalizationWrapper
	extends BaseModelWrapper<JournalArticleLocalization>
	implements JournalArticleLocalization,
			   ModelWrapper<JournalArticleLocalization> {

	public JournalArticleLocalizationWrapper(
		JournalArticleLocalization journalArticleLocalization) {

		super(journalArticleLocalization);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("articleLocalizationId", getArticleLocalizationId());
		attributes.put("companyId", getCompanyId());
		attributes.put("articlePK", getArticlePK());
		attributes.put("title", getTitle());
		attributes.put("description", getDescription());
		attributes.put("languageId", getLanguageId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

		Long articleLocalizationId = (Long)attributes.get(
			"articleLocalizationId");

		if (articleLocalizationId != null) {
			setArticleLocalizationId(articleLocalizationId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long articlePK = (Long)attributes.get("articlePK");

		if (articlePK != null) {
			setArticlePK(articlePK);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String languageId = (String)attributes.get("languageId");

		if (languageId != null) {
			setLanguageId(languageId);
		}
	}

	@Override
	public JournalArticleLocalization cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the article localization ID of this journal article localization.
	 *
	 * @return the article localization ID of this journal article localization
	 */
	@Override
	public long getArticleLocalizationId() {
		return model.getArticleLocalizationId();
	}

	/**
	 * Returns the article pk of this journal article localization.
	 *
	 * @return the article pk of this journal article localization
	 */
	@Override
	public long getArticlePK() {
		return model.getArticlePK();
	}

	/**
	 * Returns the company ID of this journal article localization.
	 *
	 * @return the company ID of this journal article localization
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the ct collection ID of this journal article localization.
	 *
	 * @return the ct collection ID of this journal article localization
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the description of this journal article localization.
	 *
	 * @return the description of this journal article localization
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the language ID of this journal article localization.
	 *
	 * @return the language ID of this journal article localization
	 */
	@Override
	public String getLanguageId() {
		return model.getLanguageId();
	}

	/**
	 * Returns the mvcc version of this journal article localization.
	 *
	 * @return the mvcc version of this journal article localization
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this journal article localization.
	 *
	 * @return the primary key of this journal article localization
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the title of this journal article localization.
	 *
	 * @return the title of this journal article localization
	 */
	@Override
	public String getTitle() {
		return model.getTitle();
	}

	/**
	 * Sets the article localization ID of this journal article localization.
	 *
	 * @param articleLocalizationId the article localization ID of this journal article localization
	 */
	@Override
	public void setArticleLocalizationId(long articleLocalizationId) {
		model.setArticleLocalizationId(articleLocalizationId);
	}

	/**
	 * Sets the article pk of this journal article localization.
	 *
	 * @param articlePK the article pk of this journal article localization
	 */
	@Override
	public void setArticlePK(long articlePK) {
		model.setArticlePK(articlePK);
	}

	/**
	 * Sets the company ID of this journal article localization.
	 *
	 * @param companyId the company ID of this journal article localization
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the ct collection ID of this journal article localization.
	 *
	 * @param ctCollectionId the ct collection ID of this journal article localization
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the description of this journal article localization.
	 *
	 * @param description the description of this journal article localization
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the language ID of this journal article localization.
	 *
	 * @param languageId the language ID of this journal article localization
	 */
	@Override
	public void setLanguageId(String languageId) {
		model.setLanguageId(languageId);
	}

	/**
	 * Sets the mvcc version of this journal article localization.
	 *
	 * @param mvccVersion the mvcc version of this journal article localization
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this journal article localization.
	 *
	 * @param primaryKey the primary key of this journal article localization
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the title of this journal article localization.
	 *
	 * @param title the title of this journal article localization
	 */
	@Override
	public void setTitle(String title) {
		model.setTitle(title);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<JournalArticleLocalization, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<JournalArticleLocalization, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected JournalArticleLocalizationWrapper wrap(
		JournalArticleLocalization journalArticleLocalization) {

		return new JournalArticleLocalizationWrapper(
			journalArticleLocalization);
	}

}
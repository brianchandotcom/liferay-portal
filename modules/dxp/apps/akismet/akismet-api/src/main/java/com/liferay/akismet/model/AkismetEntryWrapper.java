/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.akismet.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AkismetEntry}.
 * </p>
 *
 * @author Jamie Sammons
 * @see AkismetEntry
 * @generated
 */
public class AkismetEntryWrapper
	extends BaseModelWrapper<AkismetEntry>
	implements AkismetEntry, ModelWrapper<AkismetEntry> {

	public AkismetEntryWrapper(AkismetEntry akismetEntry) {
		super(akismetEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("akismetEntryId", getAkismetEntryId());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("type", getType());
		attributes.put("permalink", getPermalink());
		attributes.put("referrer", getReferrer());
		attributes.put("userAgent", getUserAgent());
		attributes.put("userIP", getUserIP());
		attributes.put("userURL", getUserURL());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long akismetEntryId = (Long)attributes.get("akismetEntryId");

		if (akismetEntryId != null) {
			setAkismetEntryId(akismetEntryId);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String permalink = (String)attributes.get("permalink");

		if (permalink != null) {
			setPermalink(permalink);
		}

		String referrer = (String)attributes.get("referrer");

		if (referrer != null) {
			setReferrer(referrer);
		}

		String userAgent = (String)attributes.get("userAgent");

		if (userAgent != null) {
			setUserAgent(userAgent);
		}

		String userIP = (String)attributes.get("userIP");

		if (userIP != null) {
			setUserIP(userIP);
		}

		String userURL = (String)attributes.get("userURL");

		if (userURL != null) {
			setUserURL(userURL);
		}
	}

	@Override
	public AkismetEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the akismet entry ID of this akismet entry.
	 *
	 * @return the akismet entry ID of this akismet entry
	 */
	@Override
	public long getAkismetEntryId() {
		return model.getAkismetEntryId();
	}

	/**
	 * Returns the fully qualified class name of this akismet entry.
	 *
	 * @return the fully qualified class name of this akismet entry
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this akismet entry.
	 *
	 * @return the class name ID of this akismet entry
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this akismet entry.
	 *
	 * @return the class pk of this akismet entry
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the modified date of this akismet entry.
	 *
	 * @return the modified date of this akismet entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the permalink of this akismet entry.
	 *
	 * @return the permalink of this akismet entry
	 */
	@Override
	public String getPermalink() {
		return model.getPermalink();
	}

	/**
	 * Returns the primary key of this akismet entry.
	 *
	 * @return the primary key of this akismet entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the referrer of this akismet entry.
	 *
	 * @return the referrer of this akismet entry
	 */
	@Override
	public String getReferrer() {
		return model.getReferrer();
	}

	/**
	 * Returns the type of this akismet entry.
	 *
	 * @return the type of this akismet entry
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	 * Returns the user agent of this akismet entry.
	 *
	 * @return the user agent of this akismet entry
	 */
	@Override
	public String getUserAgent() {
		return model.getUserAgent();
	}

	/**
	 * Returns the user ip of this akismet entry.
	 *
	 * @return the user ip of this akismet entry
	 */
	@Override
	public String getUserIP() {
		return model.getUserIP();
	}

	/**
	 * Returns the user url of this akismet entry.
	 *
	 * @return the user url of this akismet entry
	 */
	@Override
	public String getUserURL() {
		return model.getUserURL();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the akismet entry ID of this akismet entry.
	 *
	 * @param akismetEntryId the akismet entry ID of this akismet entry
	 */
	@Override
	public void setAkismetEntryId(long akismetEntryId) {
		model.setAkismetEntryId(akismetEntryId);
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this akismet entry.
	 *
	 * @param classNameId the class name ID of this akismet entry
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this akismet entry.
	 *
	 * @param classPK the class pk of this akismet entry
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the modified date of this akismet entry.
	 *
	 * @param modifiedDate the modified date of this akismet entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the permalink of this akismet entry.
	 *
	 * @param permalink the permalink of this akismet entry
	 */
	@Override
	public void setPermalink(String permalink) {
		model.setPermalink(permalink);
	}

	/**
	 * Sets the primary key of this akismet entry.
	 *
	 * @param primaryKey the primary key of this akismet entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the referrer of this akismet entry.
	 *
	 * @param referrer the referrer of this akismet entry
	 */
	@Override
	public void setReferrer(String referrer) {
		model.setReferrer(referrer);
	}

	/**
	 * Sets the type of this akismet entry.
	 *
	 * @param type the type of this akismet entry
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the user agent of this akismet entry.
	 *
	 * @param userAgent the user agent of this akismet entry
	 */
	@Override
	public void setUserAgent(String userAgent) {
		model.setUserAgent(userAgent);
	}

	/**
	 * Sets the user ip of this akismet entry.
	 *
	 * @param userIP the user ip of this akismet entry
	 */
	@Override
	public void setUserIP(String userIP) {
		model.setUserIP(userIP);
	}

	/**
	 * Sets the user url of this akismet entry.
	 *
	 * @param userURL the user url of this akismet entry
	 */
	@Override
	public void setUserURL(String userURL) {
		model.setUserURL(userURL);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected AkismetEntryWrapper wrap(AkismetEntry akismetEntry) {
		return new AkismetEntryWrapper(akismetEntry);
	}

}
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.akismet.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Jamie Sammons
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class AkismetEntrySoap implements Serializable {

	public static AkismetEntrySoap toSoapModel(AkismetEntry model) {
		AkismetEntrySoap soapModel = new AkismetEntrySoap();

		soapModel.setAkismetEntryId(model.getAkismetEntryId());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setType(model.getType());
		soapModel.setPermalink(model.getPermalink());
		soapModel.setReferrer(model.getReferrer());
		soapModel.setUserAgent(model.getUserAgent());
		soapModel.setUserIP(model.getUserIP());
		soapModel.setUserURL(model.getUserURL());

		return soapModel;
	}

	public static AkismetEntrySoap[] toSoapModels(AkismetEntry[] models) {
		AkismetEntrySoap[] soapModels = new AkismetEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AkismetEntrySoap[][] toSoapModels(AkismetEntry[][] models) {
		AkismetEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new AkismetEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new AkismetEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AkismetEntrySoap[] toSoapModels(List<AkismetEntry> models) {
		List<AkismetEntrySoap> soapModels = new ArrayList<AkismetEntrySoap>(
			models.size());

		for (AkismetEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new AkismetEntrySoap[soapModels.size()]);
	}

	public AkismetEntrySoap() {
	}

	public long getPrimaryKey() {
		return _akismetEntryId;
	}

	public void setPrimaryKey(long pk) {
		setAkismetEntryId(pk);
	}

	public long getAkismetEntryId() {
		return _akismetEntryId;
	}

	public void setAkismetEntryId(long akismetEntryId) {
		_akismetEntryId = akismetEntryId;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	public String getPermalink() {
		return _permalink;
	}

	public void setPermalink(String permalink) {
		_permalink = permalink;
	}

	public String getReferrer() {
		return _referrer;
	}

	public void setReferrer(String referrer) {
		_referrer = referrer;
	}

	public String getUserAgent() {
		return _userAgent;
	}

	public void setUserAgent(String userAgent) {
		_userAgent = userAgent;
	}

	public String getUserIP() {
		return _userIP;
	}

	public void setUserIP(String userIP) {
		_userIP = userIP;
	}

	public String getUserURL() {
		return _userURL;
	}

	public void setUserURL(String userURL) {
		_userURL = userURL;
	}

	private long _akismetEntryId;
	private Date _modifiedDate;
	private long _classNameId;
	private long _classPK;
	private String _type;
	private String _permalink;
	private String _referrer;
	private String _userAgent;
	private String _userIP;
	private String _userURL;

}
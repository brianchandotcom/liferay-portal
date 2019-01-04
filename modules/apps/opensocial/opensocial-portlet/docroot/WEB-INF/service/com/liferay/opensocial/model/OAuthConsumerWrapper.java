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

package com.liferay.opensocial.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;

/**
 * <p>
 * This class is a wrapper for {@link OAuthConsumer}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuthConsumer
 * @generated
 */
@ProviderType
public class OAuthConsumerWrapper extends BaseModelWrapper<OAuthConsumer>
	implements OAuthConsumer, ModelWrapper<OAuthConsumer> {
	public OAuthConsumerWrapper(OAuthConsumer oAuthConsumer) {
		super(oAuthConsumer);
	}

	/**
	* Returns the company ID of this o auth consumer.
	*
	* @return the company ID of this o auth consumer
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the consumer key of this o auth consumer.
	*
	* @return the consumer key of this o auth consumer
	*/
	@Override
	public String getConsumerKey() {
		return model.getConsumerKey();
	}

	/**
	* Returns the consumer secret of this o auth consumer.
	*
	* @return the consumer secret of this o auth consumer
	*/
	@Override
	public String getConsumerSecret() {
		return model.getConsumerSecret();
	}

	/**
	* Returns the create date of this o auth consumer.
	*
	* @return the create date of this o auth consumer
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the gadget key of this o auth consumer.
	*
	* @return the gadget key of this o auth consumer
	*/
	@Override
	public String getGadgetKey() {
		return model.getGadgetKey();
	}

	@Override
	public String getKeyName() {
		return model.getKeyName();
	}

	/**
	* Returns the key type of this o auth consumer.
	*
	* @return the key type of this o auth consumer
	*/
	@Override
	public String getKeyType() {
		return model.getKeyType();
	}

	/**
	* Returns the modified date of this o auth consumer.
	*
	* @return the modified date of this o auth consumer
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the o auth consumer ID of this o auth consumer.
	*
	* @return the o auth consumer ID of this o auth consumer
	*/
	@Override
	public long getOAuthConsumerId() {
		return model.getOAuthConsumerId();
	}

	/**
	* Returns the primary key of this o auth consumer.
	*
	* @return the primary key of this o auth consumer
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the service name of this o auth consumer.
	*
	* @return the service name of this o auth consumer
	*/
	@Override
	public String getServiceName() {
		return model.getServiceName();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the company ID of this o auth consumer.
	*
	* @param companyId the company ID of this o auth consumer
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the consumer key of this o auth consumer.
	*
	* @param consumerKey the consumer key of this o auth consumer
	*/
	@Override
	public void setConsumerKey(String consumerKey) {
		model.setConsumerKey(consumerKey);
	}

	/**
	* Sets the consumer secret of this o auth consumer.
	*
	* @param consumerSecret the consumer secret of this o auth consumer
	*/
	@Override
	public void setConsumerSecret(String consumerSecret) {
		model.setConsumerSecret(consumerSecret);
	}

	/**
	* Sets the create date of this o auth consumer.
	*
	* @param createDate the create date of this o auth consumer
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the gadget key of this o auth consumer.
	*
	* @param gadgetKey the gadget key of this o auth consumer
	*/
	@Override
	public void setGadgetKey(String gadgetKey) {
		model.setGadgetKey(gadgetKey);
	}

	@Override
	public void setKeyName(String keyName) {
		model.setKeyName(keyName);
	}

	/**
	* Sets the key type of this o auth consumer.
	*
	* @param keyType the key type of this o auth consumer
	*/
	@Override
	public void setKeyType(String keyType) {
		model.setKeyType(keyType);
	}

	/**
	* Sets the modified date of this o auth consumer.
	*
	* @param modifiedDate the modified date of this o auth consumer
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the o auth consumer ID of this o auth consumer.
	*
	* @param oAuthConsumerId the o auth consumer ID of this o auth consumer
	*/
	@Override
	public void setOAuthConsumerId(long oAuthConsumerId) {
		model.setOAuthConsumerId(oAuthConsumerId);
	}

	/**
	* Sets the primary key of this o auth consumer.
	*
	* @param primaryKey the primary key of this o auth consumer
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the service name of this o auth consumer.
	*
	* @param serviceName the service name of this o auth consumer
	*/
	@Override
	public void setServiceName(String serviceName) {
		model.setServiceName(serviceName);
	}

	@Override
	protected OAuthConsumerWrapper wrap(OAuthConsumer oAuthConsumer) {
		return new OAuthConsumerWrapper(oAuthConsumer);
	}
}
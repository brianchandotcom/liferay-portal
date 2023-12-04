/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.payment.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
@Generated("")
@GraphQLName("Payment")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Payment")
public class Payment implements Serializable {

	public static Payment toDTO(String json) {
		return ObjectMapperUtil.readValue(Payment.class, json);
	}

	public static Payment unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(Payment.class, json);
	}

	@Schema
	@Valid
	public Map<String, Map<String, String>> getActions() {
		return actions;
	}

	public void setActions(Map<String, Map<String, String>> actions) {
		this.actions = actions;
	}

	@JsonIgnore
	public void setActions(
		UnsafeSupplier<Map<String, Map<String, String>>, Exception>
			actionsUnsafeSupplier) {

		try {
			actions = actionsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Map<String, Map<String, String>> actions;

	@Schema(example = "101")
	@Valid
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@JsonIgnore
	public void setAmount(
		UnsafeSupplier<BigDecimal, Exception> amountUnsafeSupplier) {

		try {
			amount = amountUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal amount;

	@Schema(example = "$ 101.00")
	public String getAmountFormatted() {
		return amountFormatted;
	}

	public void setAmountFormatted(String amountFormatted) {
		this.amountFormatted = amountFormatted;
	}

	@JsonIgnore
	public void setAmountFormatted(
		UnsafeSupplier<String, Exception> amountFormattedUnsafeSupplier) {

		try {
			amountFormatted = amountFormattedUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String amountFormatted;

	@Schema
	public String getCallbackURL() {
		return callbackURL;
	}

	public void setCallbackURL(String callbackURL) {
		this.callbackURL = callbackURL;
	}

	@JsonIgnore
	public void setCallbackURL(
		UnsafeSupplier<String, Exception> callbackURLUnsafeSupplier) {

		try {
			callbackURL = callbackURLUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String callbackURL;

	@Schema
	public String getCancelURL() {
		return cancelURL;
	}

	public void setCancelURL(String cancelURL) {
		this.cancelURL = cancelURL;
	}

	@JsonIgnore
	public void setCancelURL(
		UnsafeSupplier<String, Exception> cancelURLUnsafeSupplier) {

		try {
			cancelURL = cancelURLUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String cancelURL;

	@DecimalMin("0")
	@Schema(example = "30130")
	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	@JsonIgnore
	public void setChannelId(
		UnsafeSupplier<Long, Exception> channelIdUnsafeSupplier) {

		try {
			channelId = channelIdUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long channelId;

	@Schema
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@JsonIgnore
	public void setComment(
		UnsafeSupplier<String, Exception> commentUnsafeSupplier) {

		try {
			comment = commentUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String comment;

	@Schema(example = "2023-12-01")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@JsonIgnore
	public void setCreateDate(
		UnsafeSupplier<Date, Exception> createDateUnsafeSupplier) {

		try {
			createDate = createDateUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Date createDate;

	@Schema(example = "USD")
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@JsonIgnore
	public void setCurrencyCode(
		UnsafeSupplier<String, Exception> currencyCodeUnsafeSupplier) {

		try {
			currencyCode = currencyCodeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String currencyCode;

	@Schema
	public String getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(String errorMessages) {
		this.errorMessages = errorMessages;
	}

	@JsonIgnore
	public void setErrorMessages(
		UnsafeSupplier<String, Exception> errorMessagesUnsafeSupplier) {

		try {
			errorMessages = errorMessagesUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String errorMessages;

	@Schema(example = "AB-34098-789-N")
	public String getExternalReferenceCode() {
		return externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		this.externalReferenceCode = externalReferenceCode;
	}

	@JsonIgnore
	public void setExternalReferenceCode(
		UnsafeSupplier<String, Exception> externalReferenceCodeUnsafeSupplier) {

		try {
			externalReferenceCode = externalReferenceCodeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String externalReferenceCode;

	@DecimalMin("0")
	@Schema(example = "30130")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Long id;

	@Schema(example = "en_US")
	public String getLanguageId() {
		return languageId;
	}

	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}

	@JsonIgnore
	public void setLanguageId(
		UnsafeSupplier<String, Exception> languageIdUnsafeSupplier) {

		try {
			languageId = languageIdUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String languageId;

	@Schema(example = "money-order")
	public String getPaymentIntegrationKey() {
		return paymentIntegrationKey;
	}

	public void setPaymentIntegrationKey(String paymentIntegrationKey) {
		this.paymentIntegrationKey = paymentIntegrationKey;
	}

	@JsonIgnore
	public void setPaymentIntegrationKey(
		UnsafeSupplier<String, Exception> paymentIntegrationKeyUnsafeSupplier) {

		try {
			paymentIntegrationKey = paymentIntegrationKeyUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String paymentIntegrationKey;

	@DecimalMin("0")
	@Schema(example = "0")
	public Integer getPaymentIntegrationType() {
		return paymentIntegrationType;
	}

	public void setPaymentIntegrationType(Integer paymentIntegrationType) {
		this.paymentIntegrationType = paymentIntegrationType;
	}

	@JsonIgnore
	public void setPaymentIntegrationType(
		UnsafeSupplier<Integer, Exception>
			paymentIntegrationTypeUnsafeSupplier) {

		try {
			paymentIntegrationType = paymentIntegrationTypeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Integer paymentIntegrationType;

	@DecimalMin("0")
	@Schema(example = "0")
	public Integer getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	@JsonIgnore
	public void setPaymentStatus(
		UnsafeSupplier<Integer, Exception> paymentStatusUnsafeSupplier) {

		try {
			paymentStatus = paymentStatusUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Integer paymentStatus;

	@Schema
	@Valid
	public Status getPaymentStatusStatus() {
		return paymentStatusStatus;
	}

	public void setPaymentStatusStatus(Status paymentStatusStatus) {
		this.paymentStatusStatus = paymentStatusStatus;
	}

	@JsonIgnore
	public void setPaymentStatusStatus(
		UnsafeSupplier<Status, Exception> paymentStatusStatusUnsafeSupplier) {

		try {
			paymentStatusStatus = paymentStatusStatusUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Status paymentStatusStatus;

	@Schema(example = "product-defect")
	public String getReasonKey() {
		return reasonKey;
	}

	public void setReasonKey(String reasonKey) {
		this.reasonKey = reasonKey;
	}

	@JsonIgnore
	public void setReasonKey(
		UnsafeSupplier<String, Exception> reasonKeyUnsafeSupplier) {

		try {
			reasonKey = reasonKeyUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String reasonKey;

	@Schema(
		example = "{en_US=Product Defect, hr_HR=Product Defect HR, hu_HU=Product Defect HU}"
	)
	@Valid
	public Map<String, String> getReasonName() {
		return reasonName;
	}

	public void setReasonName(Map<String, String> reasonName) {
		this.reasonName = reasonName;
	}

	@JsonIgnore
	public void setReasonName(
		UnsafeSupplier<Map<String, String>, Exception>
			reasonNameUnsafeSupplier) {

		try {
			reasonName = reasonNameUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Map<String, String> reasonName;

	@Schema
	public String getRedirectURL() {
		return redirectURL;
	}

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}

	@JsonIgnore
	public void setRedirectURL(
		UnsafeSupplier<String, Exception> redirectURLUnsafeSupplier) {

		try {
			redirectURL = redirectURLUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String redirectURL;

	@DecimalMin("0")
	@Schema(example = "30130")
	public Long getRelatedItemId() {
		return relatedItemId;
	}

	public void setRelatedItemId(Long relatedItemId) {
		this.relatedItemId = relatedItemId;
	}

	@JsonIgnore
	public void setRelatedItemId(
		UnsafeSupplier<Long, Exception> relatedItemIdUnsafeSupplier) {

		try {
			relatedItemId = relatedItemIdUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long relatedItemId;

	@Schema(example = "com.liferay.commerce.model.CommerceOrder")
	public String getRelatedItemName() {
		return relatedItemName;
	}

	public void setRelatedItemName(String relatedItemName) {
		this.relatedItemName = relatedItemName;
	}

	@JsonIgnore
	public void setRelatedItemName(
		UnsafeSupplier<String, Exception> relatedItemNameUnsafeSupplier) {

		try {
			relatedItemName = relatedItemNameUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String relatedItemName;

	@Schema(example = "Order")
	public String getRelatedItemNameLabel() {
		return relatedItemNameLabel;
	}

	public void setRelatedItemNameLabel(String relatedItemNameLabel) {
		this.relatedItemNameLabel = relatedItemNameLabel;
	}

	@JsonIgnore
	public void setRelatedItemNameLabel(
		UnsafeSupplier<String, Exception> relatedItemNameLabelUnsafeSupplier) {

		try {
			relatedItemNameLabel = relatedItemNameLabelUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String relatedItemNameLabel;

	@Schema
	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	@JsonIgnore
	public void setTransactionCode(
		UnsafeSupplier<String, Exception> transactionCodeUnsafeSupplier) {

		try {
			transactionCode = transactionCodeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String transactionCode;

	@Schema(example = "0")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@JsonIgnore
	public void setType(UnsafeSupplier<Integer, Exception> typeUnsafeSupplier) {
		try {
			type = typeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Integer type;

	@Schema(example = "Refund")
	public String getTypeLabel() {
		return typeLabel;
	}

	public void setTypeLabel(String typeLabel) {
		this.typeLabel = typeLabel;
	}

	@JsonIgnore
	public void setTypeLabel(
		UnsafeSupplier<String, Exception> typeLabelUnsafeSupplier) {

		try {
			typeLabel = typeLabelUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String typeLabel;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Payment)) {
			return false;
		}

		Payment payment = (Payment)object;

		return Objects.equals(toString(), payment.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (actions != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(actions));
		}

		if (amount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"amount\": ");

			sb.append(amount);
		}

		if (amountFormatted != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"amountFormatted\": ");

			sb.append("\"");

			sb.append(_escape(amountFormatted));

			sb.append("\"");
		}

		if (callbackURL != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"callbackURL\": ");

			sb.append("\"");

			sb.append(_escape(callbackURL));

			sb.append("\"");
		}

		if (cancelURL != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"cancelURL\": ");

			sb.append("\"");

			sb.append(_escape(cancelURL));

			sb.append("\"");
		}

		if (channelId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"channelId\": ");

			sb.append(channelId);
		}

		if (comment != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"comment\": ");

			sb.append("\"");

			sb.append(_escape(comment));

			sb.append("\"");
		}

		if (createDate != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"createDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(createDate));

			sb.append("\"");
		}

		if (currencyCode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"currencyCode\": ");

			sb.append("\"");

			sb.append(_escape(currencyCode));

			sb.append("\"");
		}

		if (errorMessages != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"errorMessages\": ");

			sb.append("\"");

			sb.append(_escape(errorMessages));

			sb.append("\"");
		}

		if (externalReferenceCode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(externalReferenceCode));

			sb.append("\"");
		}

		if (id != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(id);
		}

		if (languageId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"languageId\": ");

			sb.append("\"");

			sb.append(_escape(languageId));

			sb.append("\"");
		}

		if (paymentIntegrationKey != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paymentIntegrationKey\": ");

			sb.append("\"");

			sb.append(_escape(paymentIntegrationKey));

			sb.append("\"");
		}

		if (paymentIntegrationType != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paymentIntegrationType\": ");

			sb.append(paymentIntegrationType);
		}

		if (paymentStatus != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paymentStatus\": ");

			sb.append(paymentStatus);
		}

		if (paymentStatusStatus != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paymentStatusStatus\": ");

			sb.append(String.valueOf(paymentStatusStatus));
		}

		if (reasonKey != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"reasonKey\": ");

			sb.append("\"");

			sb.append(_escape(reasonKey));

			sb.append("\"");
		}

		if (reasonName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"reasonName\": ");

			sb.append(_toJSON(reasonName));
		}

		if (redirectURL != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"redirectURL\": ");

			sb.append("\"");

			sb.append(_escape(redirectURL));

			sb.append("\"");
		}

		if (relatedItemId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"relatedItemId\": ");

			sb.append(relatedItemId);
		}

		if (relatedItemName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"relatedItemName\": ");

			sb.append("\"");

			sb.append(_escape(relatedItemName));

			sb.append("\"");
		}

		if (relatedItemNameLabel != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"relatedItemNameLabel\": ");

			sb.append("\"");

			sb.append(_escape(relatedItemNameLabel));

			sb.append("\"");
		}

		if (transactionCode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"transactionCode\": ");

			sb.append("\"");

			sb.append(_escape(transactionCode));

			sb.append("\"");
		}

		if (type != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append(type);
		}

		if (typeLabel != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"typeLabel\": ");

			sb.append("\"");

			sb.append(_escape(typeLabel));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.commerce.admin.payment.dto.v1_0.Payment",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		return StringUtil.replace(
			String.valueOf(object), _JSON_ESCAPE_STRINGS[0],
			_JSON_ESCAPE_STRINGS[1]);
	}

	private static boolean _isArray(Object value) {
		if (value == null) {
			return false;
		}

		Class<?> clazz = value.getClass();

		return clazz.isArray();
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(_escape(entry.getKey()));
			sb.append("\": ");

			Object value = entry.getValue();

			if (_isArray(value)) {
				sb.append("[");

				Object[] valueArray = (Object[])value;

				for (int i = 0; i < valueArray.length; i++) {
					if (valueArray[i] instanceof String) {
						sb.append("\"");
						sb.append(valueArray[i]);
						sb.append("\"");
					}
					else {
						sb.append(valueArray[i]);
					}

					if ((i + 1) < valueArray.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof Map) {
				sb.append(_toJSON((Map<String, ?>)value));
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(value));
				sb.append("\"");
			}
			else {
				sb.append(value);
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static final String[][] _JSON_ESCAPE_STRINGS = {
		{"\\", "\"", "\b", "\f", "\n", "\r", "\t"},
		{"\\\\", "\\\"", "\\b", "\\f", "\\n", "\\r", "\\t"}
	};

	private Map<String, Serializable> _extendedProperties;

}
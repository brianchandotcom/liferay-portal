/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.rest.dto.v1_0;

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

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.validation.Valid;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Olivér Kecskeméty
 * @generated
 */
@Generated("")
@GraphQLName("User")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "User")
public class User implements Serializable {

	public static User toDTO(String json) {
		return ObjectMapperUtil.readValue(User.class, json);
	}

	public static User unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(User.class, json);
	}

	@Schema(
		description = "A Boolean value indicating the user's administrative status."
	)
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@JsonIgnore
	public void setActive(
		UnsafeSupplier<Boolean, Exception> activeUnsafeSupplier) {

		try {
			active = activeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "A Boolean value indicating the user's administrative status."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean active;

	@Schema(description = "A physical mailing address for this user.")
	@Valid
	public Object[] getAddresses() {
		return addresses;
	}

	public void setAddresses(Object[] addresses) {
		this.addresses = addresses;
	}

	@JsonIgnore
	public void setAddresses(
		UnsafeSupplier<Object[], Exception> addressesUnsafeSupplier) {

		try {
			addresses = addressesUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "A physical mailing address for this user.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Object[] addresses;

	@Schema
	@Valid
	public BaseScim getBaseScim() {
		return baseScim;
	}

	public void setBaseScim(BaseScim baseScim) {
		this.baseScim = baseScim;
	}

	@JsonIgnore
	public void setBaseScim(
		UnsafeSupplier<BaseScim, Exception> baseScimUnsafeSupplier) {

		try {
			baseScim = baseScimUnsafeSupplier.get();
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
	protected BaseScim baseScim;

	@Schema(
		description = "The name of the user, suitable for display to end-users."
	)
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@JsonIgnore
	public void setDisplayName(
		UnsafeSupplier<String, Exception> displayNameUnsafeSupplier) {

		try {
			displayName = displayNameUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "The name of the user, suitable for display to end-users."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String displayName;

	@Schema(description = "Email addresses for the User.")
	@Valid
	public MultiValuedAttribute[] getEmails() {
		return emails;
	}

	public void setEmails(MultiValuedAttribute[] emails) {
		this.emails = emails;
	}

	@JsonIgnore
	public void setEmails(
		UnsafeSupplier<MultiValuedAttribute[], Exception>
			emailsUnsafeSupplier) {

		try {
			emails = emailsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "Email addresses for the User.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected MultiValuedAttribute[] emails;

	@Schema(
		description = "A list of entitlements for the user that represent a thing the user has."
	)
	@Valid
	public MultiValuedAttribute[] getEntitlements() {
		return entitlements;
	}

	public void setEntitlements(MultiValuedAttribute[] entitlements) {
		this.entitlements = entitlements;
	}

	@JsonIgnore
	public void setEntitlements(
		UnsafeSupplier<MultiValuedAttribute[], Exception>
			entitlementsUnsafeSupplier) {

		try {
			entitlements = entitlementsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "A list of entitlements for the user that represent a thing the user has."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected MultiValuedAttribute[] entitlements;

	@Schema(
		description = "A list of groups to which the user belongs, either through direct membership, through nested groups, or dynamically calculated."
	)
	@Valid
	public MultiValuedAttribute[] getGroups() {
		return groups;
	}

	public void setGroups(MultiValuedAttribute[] groups) {
		this.groups = groups;
	}

	@JsonIgnore
	public void setGroups(
		UnsafeSupplier<MultiValuedAttribute[], Exception>
			groupsUnsafeSupplier) {

		try {
			groups = groupsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "A list of groups to which the user belongs, either through direct membership, through nested groups, or dynamically calculated."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected MultiValuedAttribute[] groups;

	@Schema(description = "Instant messaging address for the user.")
	@Valid
	public MultiValuedAttribute[] getIms() {
		return ims;
	}

	public void setIms(MultiValuedAttribute[] ims) {
		this.ims = ims;
	}

	@JsonIgnore
	public void setIms(
		UnsafeSupplier<MultiValuedAttribute[], Exception> imsUnsafeSupplier) {

		try {
			ims = imsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "Instant messaging address for the user.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected MultiValuedAttribute[] ims;

	@Schema(
		description = "Used to indicate the User's default location for purposes of localizing such items as currency, date time format, or numerical representations."
	)
	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	@JsonIgnore
	public void setLocale(
		UnsafeSupplier<String, Exception> localeUnsafeSupplier) {

		try {
			locale = localeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "Used to indicate the User's default location for purposes of localizing such items as currency, date time format, or numerical representations."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String locale;

	@Schema(description = "The components of the user's name.")
	@Valid
	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	@JsonIgnore
	public void setName(UnsafeSupplier<Name, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The components of the user's name.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Name name;

	@Schema(
		description = "The casual way to address the user in real life, e.g., \"Bob\" or \"Bobby\" instead of \"Robert\"."
	)
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@JsonIgnore
	public void setNickName(
		UnsafeSupplier<String, Exception> nickNameUnsafeSupplier) {

		try {
			nickName = nickNameUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "The casual way to address the user in real life, e.g., \"Bob\" or \"Bobby\" instead of \"Robert\"."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String nickName;

	@Schema(
		description = "This attribute is intended to be used as a means to set, replace, or compare (i.e., filter for equality) a password."
	)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	public void setPassword(
		UnsafeSupplier<String, Exception> passwordUnsafeSupplier) {

		try {
			password = passwordUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "This attribute is intended to be used as a means to set, replace, or compare (i.e., filter for equality) a password."
	)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	protected String password;

	@Schema(description = "Phone numbers for the user.")
	@Valid
	public MultiValuedAttribute[] getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(MultiValuedAttribute[] phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	@JsonIgnore
	public void setPhoneNumbers(
		UnsafeSupplier<MultiValuedAttribute[], Exception>
			phoneNumbersUnsafeSupplier) {

		try {
			phoneNumbers = phoneNumbersUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "Phone numbers for the user.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected MultiValuedAttribute[] phoneNumbers;

	@Schema(
		description = "A URI that is a uniform resource locator that points to a resource location representing the user's image."
	)
	@Valid
	public MultiValuedAttribute[] getPhotos() {
		return photos;
	}

	public void setPhotos(MultiValuedAttribute[] photos) {
		this.photos = photos;
	}

	@JsonIgnore
	public void setPhotos(
		UnsafeSupplier<MultiValuedAttribute[], Exception>
			photosUnsafeSupplier) {

		try {
			photos = photosUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "A URI that is a uniform resource locator that points to a resource location representing the user's image."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected MultiValuedAttribute[] photos;

	@Schema(
		description = "Indicates the user's preferred written or spoken languages and is generally used for selecting a localized user interface."
	)
	public String getPreferredLanguage() {
		return preferredLanguage;
	}

	public void setPreferredLanguage(String preferredLanguage) {
		this.preferredLanguage = preferredLanguage;
	}

	@JsonIgnore
	public void setPreferredLanguage(
		UnsafeSupplier<String, Exception> preferredLanguageUnsafeSupplier) {

		try {
			preferredLanguage = preferredLanguageUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "Indicates the user's preferred written or spoken languages and is generally used for selecting a localized user interface."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String preferredLanguage;

	@Schema(
		description = "A URI that is a uniform resource locator and that points to a location representing the user's online profile (e.g., a web page)."
	)
	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	@JsonIgnore
	public void setProfileUrl(
		UnsafeSupplier<String, Exception> profileUrlUnsafeSupplier) {

		try {
			profileUrl = profileUrlUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "A URI that is a uniform resource locator and that points to a location representing the user's online profile (e.g., a web page)."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String profileUrl;

	@Schema(
		description = "A list of roles for the user that collectively represent who the user is, e.g., \"Student\", \"Faculty\"."
	)
	@Valid
	public MultiValuedAttribute[] getRoles() {
		return roles;
	}

	public void setRoles(MultiValuedAttribute[] roles) {
		this.roles = roles;
	}

	@JsonIgnore
	public void setRoles(
		UnsafeSupplier<MultiValuedAttribute[], Exception> rolesUnsafeSupplier) {

		try {
			roles = rolesUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "A list of roles for the user that collectively represent who the user is, e.g., \"Student\", \"Faculty\"."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected MultiValuedAttribute[] roles;

	@Schema(
		description = "The User's time zone, in IANA Time Zone database format, also known as the \"Olson\" time zone database format (e.g., \"America/Los_Angeles\")."
	)
	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	@JsonIgnore
	public void setTimezone(
		UnsafeSupplier<String, Exception> timezoneUnsafeSupplier) {

		try {
			timezone = timezoneUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "The User's time zone, in IANA Time Zone database format, also known as the \"Olson\" time zone database format (e.g., \"America/Los_Angeles\")."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String timezone;

	@Schema(description = "The user's title, such as \"Vice President\".")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JsonIgnore
	public void setTitle(
		UnsafeSupplier<String, Exception> titleUnsafeSupplier) {

		try {
			title = titleUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The user's title, such as \"Vice President\".")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String title;

	@Schema(
		description = "A service provider's unique identifier for the user, typically used by the user to directly authenticate to the service provider."
	)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonIgnore
	public void setUserName(
		UnsafeSupplier<String, Exception> userNameUnsafeSupplier) {

		try {
			userName = userNameUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "A service provider's unique identifier for the user, typically used by the user to directly authenticate to the service provider."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String userName;

	@Schema(
		description = "Used to identify the relationship between the organization and the user."
	)
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@JsonIgnore
	public void setUserType(
		UnsafeSupplier<String, Exception> userTypeUnsafeSupplier) {

		try {
			userType = userTypeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "Used to identify the relationship between the organization and the user."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String userType;

	@Schema(
		description = "A list of certificates associated with the resource (e.g., a User)."
	)
	@Valid
	public MultiValuedAttribute[] getX509Certificates() {
		return x509Certificates;
	}

	public void setX509Certificates(MultiValuedAttribute[] x509Certificates) {
		this.x509Certificates = x509Certificates;
	}

	@JsonIgnore
	public void setX509Certificates(
		UnsafeSupplier<MultiValuedAttribute[], Exception>
			x509CertificatesUnsafeSupplier) {

		try {
			x509Certificates = x509CertificatesUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "A list of certificates associated with the resource (e.g., a User)."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected MultiValuedAttribute[] x509Certificates;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof User)) {
			return false;
		}

		User user = (User)object;

		return Objects.equals(toString(), user.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (active != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"active\": ");

			sb.append(active);
		}

		if (addresses != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"addresses\": ");

			sb.append("[");

			for (int i = 0; i < addresses.length; i++) {
				sb.append("\"");

				sb.append(_escape(addresses[i]));

				sb.append("\"");

				if ((i + 1) < addresses.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (baseScim != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"baseScim\": ");

			sb.append(String.valueOf(baseScim));
		}

		if (displayName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayName\": ");

			sb.append("\"");

			sb.append(_escape(displayName));

			sb.append("\"");
		}

		if (emails != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"emails\": ");

			sb.append("[");

			for (int i = 0; i < emails.length; i++) {
				sb.append(String.valueOf(emails[i]));

				if ((i + 1) < emails.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (entitlements != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"entitlements\": ");

			sb.append("[");

			for (int i = 0; i < entitlements.length; i++) {
				sb.append(String.valueOf(entitlements[i]));

				if ((i + 1) < entitlements.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (groups != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"groups\": ");

			sb.append("[");

			for (int i = 0; i < groups.length; i++) {
				sb.append(String.valueOf(groups[i]));

				if ((i + 1) < groups.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (ims != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"ims\": ");

			sb.append("[");

			for (int i = 0; i < ims.length; i++) {
				sb.append(String.valueOf(ims[i]));

				if ((i + 1) < ims.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (locale != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"locale\": ");

			sb.append("\"");

			sb.append(_escape(locale));

			sb.append("\"");
		}

		if (name != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append(String.valueOf(name));
		}

		if (nickName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"nickName\": ");

			sb.append("\"");

			sb.append(_escape(nickName));

			sb.append("\"");
		}

		if (password != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"password\": ");

			sb.append("\"");

			sb.append(_escape(password));

			sb.append("\"");
		}

		if (phoneNumbers != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"phoneNumbers\": ");

			sb.append("[");

			for (int i = 0; i < phoneNumbers.length; i++) {
				sb.append(String.valueOf(phoneNumbers[i]));

				if ((i + 1) < phoneNumbers.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (photos != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"photos\": ");

			sb.append("[");

			for (int i = 0; i < photos.length; i++) {
				sb.append(String.valueOf(photos[i]));

				if ((i + 1) < photos.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (preferredLanguage != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"preferredLanguage\": ");

			sb.append("\"");

			sb.append(_escape(preferredLanguage));

			sb.append("\"");
		}

		if (profileUrl != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"profileUrl\": ");

			sb.append("\"");

			sb.append(_escape(profileUrl));

			sb.append("\"");
		}

		if (roles != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"roles\": ");

			sb.append("[");

			for (int i = 0; i < roles.length; i++) {
				sb.append(String.valueOf(roles[i]));

				if ((i + 1) < roles.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (timezone != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"timezone\": ");

			sb.append("\"");

			sb.append(_escape(timezone));

			sb.append("\"");
		}

		if (title != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": ");

			sb.append("\"");

			sb.append(_escape(title));

			sb.append("\"");
		}

		if (userName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userName\": ");

			sb.append("\"");

			sb.append(_escape(userName));

			sb.append("\"");
		}

		if (userType != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userType\": ");

			sb.append("\"");

			sb.append(_escape(userType));

			sb.append("\"");
		}

		if (x509Certificates != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"x509Certificates\": ");

			sb.append("[");

			for (int i = 0; i < x509Certificates.length; i++) {
				sb.append(String.valueOf(x509Certificates[i]));

				if ((i + 1) < x509Certificates.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.scim.rest.dto.v1_0.User",
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
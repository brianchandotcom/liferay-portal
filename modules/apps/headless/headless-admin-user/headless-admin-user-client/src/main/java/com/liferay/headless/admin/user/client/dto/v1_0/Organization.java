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

package com.liferay.headless.admin.user.client.dto.v1_0;

import com.liferay.headless.admin.user.client.function.UnsafeSupplier;

import java.util.Date;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Organization {

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setComment(
		UnsafeSupplier<String, Exception> commentUnsafeSupplier) {

		try {
			comment = commentUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String comment;

	public ContactInformation getContactInformation() {
		return contactInformation;
	}

	public void setContactInformation(ContactInformation contactInformation) {
		this.contactInformation = contactInformation;
	}

	public void setContactInformation(
		UnsafeSupplier<ContactInformation, Exception>
			contactInformationUnsafeSupplier) {

		try {
			contactInformation = contactInformationUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ContactInformation contactInformation;

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setDateCreated(
		UnsafeSupplier<Date, Exception> dateCreatedUnsafeSupplier) {

		try {
			dateCreated = dateCreatedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dateCreated;

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public void setDateModified(
		UnsafeSupplier<Date, Exception> dateModifiedUnsafeSupplier) {

		try {
			dateModified = dateModifiedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dateModified;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long id;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setImage(
		UnsafeSupplier<String, Exception> imageUnsafeSupplier) {

		try {
			image = imageUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String image;

	public String[] getKeywords() {
		return keywords;
	}

	public void setKeywords(String[] keywords) {
		this.keywords = keywords;
	}

	public void setKeywords(
		UnsafeSupplier<String[], Exception> keywordsUnsafeSupplier) {

		try {
			keywords = keywordsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] keywords;

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setLocation(
		UnsafeSupplier<Location, Exception> locationUnsafeSupplier) {

		try {
			location = locationUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Location location;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String name;

	public Integer getNumberOfOrganizations() {
		return numberOfOrganizations;
	}

	public void setNumberOfOrganizations(Integer numberOfOrganizations) {
		this.numberOfOrganizations = numberOfOrganizations;
	}

	public void setNumberOfOrganizations(
		UnsafeSupplier<Integer, Exception>
			numberOfOrganizationsUnsafeSupplier) {

		try {
			numberOfOrganizations = numberOfOrganizationsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer numberOfOrganizations;

	public Organization getParentOrganization() {
		return parentOrganization;
	}

	public void setParentOrganization(Organization parentOrganization) {
		this.parentOrganization = parentOrganization;
	}

	public void setParentOrganization(
		UnsafeSupplier<Organization, Exception>
			parentOrganizationUnsafeSupplier) {

		try {
			parentOrganization = parentOrganizationUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Organization parentOrganization;

	public Service[] getServices() {
		return services;
	}

	public void setServices(Service[] services) {
		this.services = services;
	}

	public void setServices(
		UnsafeSupplier<Service[], Exception> servicesUnsafeSupplier) {

		try {
			services = servicesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Service[] services;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Organization)) {
			return false;
		}

		Organization organization = (Organization)object;

		return Objects.equals(toString(), organization.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"comment\": ");

		if (comment == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(comment);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"contactInformation\": ");

		if (contactInformation == null) {
			sb.append("null");
		}
		else {
			sb.append(contactInformation);
		}

		sb.append(", ");

		sb.append("\"dateCreated\": ");

		if (dateCreated == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(dateCreated);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"dateModified\": ");

		if (dateModified == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(dateModified);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (id == null) {
			sb.append("null");
		}
		else {
			sb.append(id);
		}

		sb.append(", ");

		sb.append("\"image\": ");

		if (image == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(image);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"keywords\": ");

		if (keywords == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < keywords.length; i++) {
				sb.append("\"");
				sb.append(keywords[i]);
				sb.append("\"");

				if ((i + 1) < keywords.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"location\": ");

		if (location == null) {
			sb.append("null");
		}
		else {
			sb.append(location);
		}

		sb.append(", ");

		sb.append("\"name\": ");

		if (name == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(name);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"numberOfOrganizations\": ");

		if (numberOfOrganizations == null) {
			sb.append("null");
		}
		else {
			sb.append(numberOfOrganizations);
		}

		sb.append(", ");

		sb.append("\"parentOrganization\": ");

		if (parentOrganization == null) {
			sb.append("null");
		}
		else {
			sb.append(parentOrganization);
		}

		sb.append(", ");

		sb.append("\"services\": ");

		if (services == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < services.length; i++) {
				sb.append(services[i]);

				if ((i + 1) < services.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

}
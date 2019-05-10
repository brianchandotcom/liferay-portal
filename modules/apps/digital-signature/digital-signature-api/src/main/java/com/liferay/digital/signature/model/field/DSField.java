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

package com.liferay.digital.signature.model.field;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface DSField<T extends DSField<?>> {

	public Boolean getAnchorIgnoreIfNotPresent();

	public String getAnchorString();

	public AnchorUnits getAnchorUnits();

	public Float getAnchorXOffset();

	public Float getAnchorYOffset();

	public String getConditionalParentLabel();

	public String getConditionalParentValue();

	public String getDocumentKey();

	public String getFieldKey();

	public String getFieldLabel();

	public Integer getFieldOrder();

	public Boolean getLocked();

	public String getName();

	public Integer getPageNumber();

	public String getParticipantKey();

	public Boolean getRequireAll();

	public Boolean getRequired();

	public Boolean getShared();

	public Boolean getTemplateLocked();

	public Boolean getTemplateRequired();

	public Float getXPosition();

	public Float getYPosition();

	public <S> S visit(DSFieldVisitor<S> dsFieldVisitor);

}
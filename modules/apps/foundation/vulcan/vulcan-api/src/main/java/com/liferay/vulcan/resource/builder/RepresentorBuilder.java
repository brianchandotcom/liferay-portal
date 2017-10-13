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

package com.liferay.vulcan.resource.builder;

import aQute.bnd.annotation.ProviderType;

import com.liferay.vulcan.alias.BinaryFunction;
import com.liferay.vulcan.resource.Representor;
import com.liferay.vulcan.resource.identifier.Identifier;

import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

/**
 * Use instances of this builder to create generic representations of your
 * domain models that Vulcan Hypermedia Writers will understand.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 * @review
 */
@ProviderType
public interface RepresentorBuilder<T, U extends Identifier> {

	/**
	 * Provide a lambda function that can be used to obtain the {@link
	 * Identifier} used for a model.
	 *
	 * @param  identifierFunction function used to obtain a model's identifier.
	 * @return builder's next step.
	 * @review
	 */
	public FirstStep<T, U> identifier(Function<T, U> identifierFunction);

	public interface FirstStep<T, U extends Identifier> {

		/**
		 * Use this method to provide information of a bidirectional relation of
		 * a linked model in the actual resource and a related collection of
		 * items of this {@link com.liferay.vulcan.resource.CollectionResource}
		 * in the related resource.
		 *
		 * @param  key name of the relation in this resource.
		 * @param  relatedKey name of the relation in the related resource.
		 * @param  modelClass class of the related model.
		 * @param  modelFunction function used to obtain the related model.
		 * @param  identifierFunction function used to obtain the identifier for
		 *         the collection.
		 * @return builder's actual step.
		 * @review
		 */
		public <S> FirstStep<T, U> addBidirectionalModel(
			String key, String relatedKey, Class<S> modelClass,
			Function<T, Optional<S>> modelFunction,
			Function<S, Identifier> identifierFunction);

		/**
		 * @param  key name of the binary resource
		 * @param  binaryFunction function used to obtain the binaries.
		 * @return builder's actual step.
		 * @review
		 */
		public FirstStep<T, U> addBinary(
			String key, BinaryFunction<T> binaryFunction);

		/**
		 * Use this method to provide information of a resource boolean field.
		 *
		 * @param  key name of the field.
		 * @param  booleanFunction function used to obtain the boolean value.
		 * @return builder's actual step.
		 * @review
		 */
		public FirstStep<T, U> addBoolean(
			String key, Function<T, Boolean> booleanFunction);

		/**
		 * Use this method to provide information of a resource date field.
		 *
		 * @param  key name of the field.
		 * @param  dateFunction function used to obtain the date value.
		 * @return builder's actual step.
		 * @review
		 */
		public FirstStep<T, U> addDate(
			String key, Function<T, Date> dateFunction);

		/**
		 * Use this method to provide information of an embeddable related
		 * model.
		 *
		 * @param  key name of the relation.
		 * @param  modelClass class of the related model.
		 * @param  modelFunction function used to obtain the related model.
		 * @return builder's actual step.
		 * @review
		 */
		public <S> FirstStep<T, U> addEmbeddedModel(
			String key, Class<S> modelClass,
			Function<T, Optional<S>> modelFunction);

		/**
		 * Use this method to provide information of a resource link.
		 *
		 * @param  key name of the field.
		 * @param  url url link's url.
		 * @return builder's actual step.
		 * @review
		 */
		public FirstStep<T, U> addLink(String key, String url);

		/**
		 * Use this method to provide information of a non embeddable related
		 * model.
		 *
		 * @param  key name of the relation.
		 * @param  modelClass class of the related model.
		 * @param  modelFunction function used to obtain the related model.
		 * @return builder's actual step.
		 * @review
		 */
		public <S> FirstStep<T, U> addLinkedModel(
			String key, Class<S> modelClass,
			Function<T, Optional<S>> modelFunction);

		/**
		 * Use this method to provide information of a resource number field.
		 *
		 * @param  key name of the field.
		 * @param  numberFunction function used to obtain the number value.
		 * @return builder's actual step.
		 * @review
		 */
		public FirstStep<T, U> addNumber(
			String key, Function<T, Number> numberFunction);

		/**
		 * Use this method to provide information of a related collection.
		 *
		 * @param  key name of the relation.
		 * @param  modelClass class of the collection's related models.
		 * @param  identifierFunction function used to obtain the identifier for
		 *         the collection.
		 * @return builder's actual step.
		 * @review
		 */
		public <S> FirstStep<T, U> addRelatedCollection(
			String key, Class<S> modelClass,
			Function<T, Identifier> identifierFunction);

		/**
		 * Use this method to provide information of a resource string field.
		 *
		 * @param  key name of the field.
		 * @param  stringFunction function used to obtain the string value.
		 * @return builder's actual step.
		 * @review
		 */
		public FirstStep<T, U> addString(
			String key, Function<T, String> stringFunction);

		/**
		 * Use this method to provide a type for this model. Multiple types are
		 * allowed.
		 *
		 * @param  type type name.
		 * @return builder's actual step.
		 * @review
		 */
		public FirstStep<T, U> addType(String type);

		/**
		 * Constructs the {@code Representor} instance with the information
		 * provided to the builder.
		 *
		 * @return the {@code Representor} instance.
		 * @review
		 */
		public Representor<T, U> build();

	}

}
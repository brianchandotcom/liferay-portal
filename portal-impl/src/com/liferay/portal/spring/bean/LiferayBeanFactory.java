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

package com.liferay.portal.spring.bean;

import com.liferay.portal.spring.aop.BaseServiceBeanAutoProxyCreator;

import java.beans.PropertyDescriptor;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * @author Preston Crary
 */
public class LiferayBeanFactory extends DefaultListableBeanFactory {

	public LiferayBeanFactory(BeanFactory parentBeanFactory) {
		super(parentBeanFactory);
	}

	@Override
	public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
		super.addBeanPostProcessor(beanPostProcessor);

		if ((beanPostProcessor instanceof
				InstantiationAwareBeanPostProcessor) &&
			!(beanPostProcessor instanceof BaseServiceBeanAutoProxyCreator)) {

			_postProcessPropertyValues = true;
		}
	}

	@Override
	protected void populateBean(
		String beanName, RootBeanDefinition rootBeanDefinition,
		BeanWrapper beanWrapper) {

		PropertyValues propertyValues = rootBeanDefinition.getPropertyValues();

		if (beanWrapper == null) {
			if (propertyValues.isEmpty()) {
				return;
			}

			throw new BeanCreationException(
				rootBeanDefinition.getResourceDescription(), beanName,
				"Unable to apply property values to null instance");
		}

		if (!_isContinueWithPropertyPopulation(
				beanName, rootBeanDefinition, beanWrapper)) {

			return;
		}

		if ((rootBeanDefinition.getResolvedAutowireMode() ==
				RootBeanDefinition.AUTOWIRE_BY_NAME) ||
			(rootBeanDefinition.getResolvedAutowireMode() ==
				RootBeanDefinition.AUTOWIRE_BY_TYPE)) {

			MutablePropertyValues mutablePropertyValues =
				new MutablePropertyValues(propertyValues);

			if (rootBeanDefinition.getResolvedAutowireMode() ==
					RootBeanDefinition.AUTOWIRE_BY_NAME) {

				autowireByName(
					beanName, rootBeanDefinition, beanWrapper,
					mutablePropertyValues);
			}

			if (rootBeanDefinition.getResolvedAutowireMode() ==
					RootBeanDefinition.AUTOWIRE_BY_TYPE) {

				autowireByType(
					beanName, rootBeanDefinition, beanWrapper,
					mutablePropertyValues);
			}

			propertyValues = mutablePropertyValues;
		}

		boolean hasInstantiationAwareBeanPostProcessors =
			hasInstantiationAwareBeanPostProcessors();

		boolean needsDependencyCheck = true;

		if (rootBeanDefinition.getDependencyCheck() ==
				RootBeanDefinition.DEPENDENCY_CHECK_NONE) {

			needsDependencyCheck = false;
		}

		if ((hasInstantiationAwareBeanPostProcessors &&
			 _postProcessPropertyValues) ||
			needsDependencyCheck) {

			PropertyDescriptor[] propertyDescriptors =
				filterPropertyDescriptorsForDependencyCheck(beanWrapper, true);

			if (hasInstantiationAwareBeanPostProcessors) {
				for (BeanPostProcessor beanPostProcessor :
						getBeanPostProcessors()) {

					if (beanPostProcessor instanceof
							InstantiationAwareBeanPostProcessor) {

						InstantiationAwareBeanPostProcessor
							instantiationAwareBeanPostProcessor =
								(InstantiationAwareBeanPostProcessor)
									beanPostProcessor;

						propertyValues =
							instantiationAwareBeanPostProcessor.
								postProcessPropertyValues(
									propertyValues, propertyDescriptors,
									beanWrapper.getWrappedInstance(), beanName);

						if (propertyValues == null) {
							return;
						}
					}
				}
			}

			if (needsDependencyCheck) {
				checkDependencies(
					beanName, rootBeanDefinition, propertyDescriptors,
					propertyValues);
			}
		}

		applyPropertyValues(
			beanName, rootBeanDefinition, beanWrapper, propertyValues);
	}

	private boolean _isContinueWithPropertyPopulation(
		String beanName, RootBeanDefinition rootBeanDefinition,
		BeanWrapper beanWrapper) {

		if (!rootBeanDefinition.isSynthetic() &&
			hasInstantiationAwareBeanPostProcessors()) {

			for (BeanPostProcessor beanPostProcessor :
					getBeanPostProcessors()) {

				if (beanPostProcessor instanceof
						InstantiationAwareBeanPostProcessor) {

					InstantiationAwareBeanPostProcessor
						instantiationAwareBeanPostProcessor =
							(InstantiationAwareBeanPostProcessor)
								beanPostProcessor;

					if (!instantiationAwareBeanPostProcessor.
							postProcessAfterInstantiation(
								beanWrapper.getWrappedInstance(), beanName)) {

						return false;
					}
				}
			}
		}

		return true;
	}

	private boolean _postProcessPropertyValues;

}
package com.liferay.document.selector;

import java.util.Set;

/**
 * @author Iván Zaera
 */
public interface ItemSelectorCriterion {

	public Set<Class<?>> getAvailableReturnTypes();

	public Set<Class<?>> getDesiredReturnTypes();

	public void setDesiredReturnTypes(Class<?>... desiredReturnTypes);

}
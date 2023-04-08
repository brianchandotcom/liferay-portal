package com.liferay.object.internal.system;

import com.liferay.object.system.ModifiableSystemObjectDefinitions;

import org.osgi.service.component.annotations.Component;

@Component(service = ModifiableSystemObjectDefinitions.class)
public class BookmarksModifiableSystemObjectDefinitions
	implements ModifiableSystemObjectDefinitions {

	@Override
	public String getResourcePath() {
		return "com/liferay/object/internal/system/dependencies/bookmarks.json";
	}

}
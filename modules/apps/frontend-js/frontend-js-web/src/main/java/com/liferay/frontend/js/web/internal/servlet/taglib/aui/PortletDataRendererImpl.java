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

package com.liferay.frontend.js.web.internal.servlet.taglib.aui;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.taglib.aui.AMDRequire;
import com.liferay.portal.kernel.servlet.taglib.aui.ESMImport;
import com.liferay.portal.kernel.servlet.taglib.aui.PortletData;
import com.liferay.portal.kernel.servlet.taglib.aui.PortletDataRenderer;

import java.io.IOException;
import java.io.Writer;

import java.util.Collection;

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera Avellón
 */
@Component(immediate = true, service = PortletDataRenderer.class)
public class PortletDataRendererImpl implements PortletDataRenderer {

	@Override
	public void writeTo(Writer writer, Collection<PortletData> portletDatas)
		throws IOException {

		PortletDataCollection portletDataCollection = new PortletDataCollection(
			portletDatas);

		if (portletDataCollection.isUseESM()) {
			writer.write("<script type=\"module\">\n// <![CDATA[\n");
		}
		else {
			writer.write("<script type=\"text/javascript\">\n// <![CDATA[\n");
		}

		Collection<ESMImport> esmImports =
			portletDataCollection.getESMImports();

		if (!esmImports.isEmpty()) {
			_writeESMProlog(writer, esmImports);
		}

		writer.write(portletDataCollection.getRawContent());

		Collection<AMDRequire> amdRequires =
			portletDataCollection.getAMDRequires();

		if (!amdRequires.isEmpty()) {
			_writeAMDProlog(writer, amdRequires);
		}

		Collection<String> auiModules = portletDataCollection.getAUIModules();

		if (!auiModules.isEmpty()) {
			_writeAUIProlog(writer, auiModules);
		}

		writer.write(portletDataCollection.getContent());

		if (!auiModules.isEmpty()) {
			_writeAUIEpilog(writer);
		}

		if (!amdRequires.isEmpty()) {
			_writeAMDEpilog(writer);
		}

		writer.write("\n// ]]>\n</script>");
	}

	private void _writeAMDEpilog(Writer writer) throws IOException {
		writer.write("} catch (err) {\n");
		writer.write("\tconsole.error(err);\n");
		writer.write("}\n");

		writer.write("});\n");
	}

	private void _writeAMDProlog(
			Writer writer, Collection<AMDRequire> amdRequires)
		throws IOException {

		writer.write("Liferay.Loader.require(\n");

		for (AMDRequire amdRequire : amdRequires) {
			writer.write("  '");
			writer.write(amdRequire.getModule());
			writer.write("',\n");
		}

		writer.write("function(");

		String delimiter = StringPool.BLANK;

		for (AMDRequire amdRequire : amdRequires) {
			writer.write(delimiter);
			writer.write(amdRequire.getAlias());

			delimiter = StringPool.COMMA_AND_SPACE;
		}

		writer.write(") {\n");
		writer.write("try {\n");
	}

	private void _writeAUIEpilog(Writer writer) throws IOException {
		writer.write("});\n");
	}

	private void _writeAUIProlog(Writer writer, Collection<String> auiModules)
		throws IOException {

		writer.write("AUI().use(\n");

		for (String auiModule : auiModules) {
			writer.write("  '");
			writer.write(auiModule);
			writer.write("',\n");
		}

		writer.write("function(A) {\n");
	}

	private void _writeESMProlog(
			Writer writer, Collection<ESMImport> esmImports)
		throws IOException {

		for (ESMImport esmImport : esmImports) {
			writer.write("import {");
			writer.write(esmImport.getSymbol());

			String alias = esmImport.getAlias();

			if (!alias.equals(esmImport.getSymbol())) {
				writer.write(" as ");
				writer.write(alias);
			}

			writer.write("} from '");
			writer.write(esmImport.getModule());
			writer.write("';\n");
		}
	}

}
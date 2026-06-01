/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.semantic.search.cli.util;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Tiny argument parser. Recognizes long flags only:
 *   --top N
 *   --format text|json
 *   --force
 *   --path PREFIX
 *
 * Anything else is a positional argument, accumulated in order.
 *
 * Intentionally limited. Subcommands validate their own positional
 * counts and unknown-flag handling; this class is just the split.
 *
 * @author JR Houn
 */
public class Arguments {

	public Arguments(String[] args) {
		List<String> positional = new ArrayList<>();

		boolean force = false;

		int i = 0;

		while (i < args.length) {
			String arg = args[i];

			if (arg.equals("--top")) {
				_top = GetterUtil.getInteger(args[++i], _top);
			}
			else if (arg.equals("--format")) {
				_format = args[++i];
			}
			else if (arg.equals("--force")) {
				force = true;
			}
			else if (arg.equals("--path")) {
				_path = _normalizePath(args[++i]);
			}
			else {
				positional.add(arg);
			}

			i++;
		}

		_force = force;
		_positional = positional;
	}

	public String getFormat() {
		return _format;
	}

	/**
	 * The directory-prefix scope for a query, relative to the index's
	 * ingest root (for example, "modules/apps/headless"). Empty when no
	 * --path flag was given, meaning the whole index is searched.
	 */
	public String getPath() {
		return _path;
	}

	public List<String> getPositional() {
		return _positional;
	}

	public int getTop() {
		return _top;
	}

	public boolean isForce() {
		return _force;
	}

	private String _normalizePath(String path) {
		String normalized = StringUtil.replace(path, '\\', '/');

		while (normalized.startsWith("./")) {
			normalized = normalized.substring(2);
		}

		while (normalized.endsWith("/")) {
			normalized = normalized.substring(0, normalized.length() - 1);
		}

		return normalized;
	}

	private final boolean _force;
	private String _format = "text";
	private String _path = "";
	private final List<String> _positional;
	private int _top = 5;

}
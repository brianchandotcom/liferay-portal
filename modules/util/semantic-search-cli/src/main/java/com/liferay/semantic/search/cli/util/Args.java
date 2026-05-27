/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.semantic.search.cli.util;

import com.liferay.portal.kernel.util.GetterUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Tiny argument parser. Recognizes long flags only:
 *   --top N
 *   --format text|json
 *   --force
 *
 * Anything else is a positional argument, accumulated in order.
 *
 * Intentionally limited. Subcommands validate their own positional
 * counts and unknown-flag handling; this class is just the split.
 *
 * @author JR Houn
 */
public class Args {

	public Args(String[] args) {
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
			else {
				positional.add(arg);
			}

			i++;
		}

		_force = force;
		_positional = positional;
	}

	public boolean force() {
		return _force;
	}

	public String format() {
		return _format;
	}

	public List<String> positional() {
		return _positional;
	}

	public int top() {
		return _top;
	}

	private final boolean _force;
	private String _format = "text";
	private final List<String> _positional;
	private int _top = 5;

}
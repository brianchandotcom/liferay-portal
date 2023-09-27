/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.whip.instrument;

import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * <p>
 * See https://issues.liferay.com/browse/LPS-44718.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class BackwardCompatibleMethodNode extends MethodNode {

	public BackwardCompatibleMethodNode(
		int access, String name, String desc, String signature,
		String[] exceptions) {

		super(Opcodes.ASM5, access, name, desc, signature, exceptions);
	}

	@Override
	protected LabelNode getLabelNode(Label label) {
		if (label.info instanceof LabelNode) {
			return (LabelNode)label.info;
		}

		return new LabelNode(label);
	}

}
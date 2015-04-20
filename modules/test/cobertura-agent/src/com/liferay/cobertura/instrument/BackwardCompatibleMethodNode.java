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

package com.liferay.cobertura.instrument;

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
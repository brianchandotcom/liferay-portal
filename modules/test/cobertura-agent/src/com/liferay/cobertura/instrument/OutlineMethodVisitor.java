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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sourceforge.cobertura.coveragedata.ClassData;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.MethodNode;

/**
 * @author Shuyang Zhou
 */
public class OutlineMethodVisitor extends MethodVisitor {

	public OutlineMethodVisitor(
		ClassData classData, MethodVisitor methodVisitor, int access,
		String name, String desc, String signature, String[] exceptions) {

		super(
			Opcodes.ASM5,
			new BackwardCompatibleMethodNode(
				access, name, desc, signature, exceptions));

		_classData = classData;
		_methodVisitor = methodVisitor;

		_methodNode = (MethodNode)mv;
	}

	@Override
	public void visitEnd() {
		super.visitEnd();

		MethodVisitor methodVisitor = _methodVisitor;

		if (!_lineLabels.isEmpty()) {
			methodVisitor = new TouchMethodVisitor(
				_classData.getName(), _methodNode, _methodVisitor, _jumpLabels,
				_lineLabels, _switchLabels);
		}

		_methodNode.accept(methodVisitor);
	}

	@Override
	public void visitJumpInsn(int opcode, Label label) {
		if ((_currentLine != 0) && (opcode != Opcodes.GOTO) &&
			(opcode != Opcodes.JSR)) {

			_classData.addLineJump(_currentLine, _currentJump);

			_jumpLabels.add(label);
		}

		super.visitJumpInsn(opcode, label);
	}

	@Override
	public void visitLineNumber(int line, Label start) {
		_currentLine = line;
		_currentJump = 0;
		_currentSwitch = 0;

		_classData.addLine(_currentLine, _methodNode.name, _methodNode.desc);

		_lineLabels.put(start, line);
	}

	@Override
	public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
		super.visitLookupSwitchInsn(dflt, keys, labels);

		if (_currentLine != 0) {
			_switchLabels.put(
				dflt, new SwitchHolder(_currentLine, _currentSwitch, -1));

			for (int i = 0; i < labels.length; i++) {
				_switchLabels.put(
					labels[i],
					new SwitchHolder(_currentLine, _currentSwitch, i));
			}

			_classData.addLineSwitch(_currentLine, _currentSwitch++, keys);
		}
	}

	@Override
	public void visitTableSwitchInsn(
		int min, int max, Label dflt, Label... labels) {

		super.visitTableSwitchInsn(min, max, dflt, labels);

		if (_currentLine != 0) {
			_switchLabels.put(
				dflt, new SwitchHolder(_currentLine, _currentSwitch, -1));

			for (int i = 0; i < labels.length; i++) {
				_switchLabels.put(
					labels[i],
					new SwitchHolder(_currentLine, _currentSwitch, i));
			}

			_classData.addLineSwitch(_currentLine, _currentSwitch++, min, max);
		}
	}

	private final ClassData _classData;
	private int _currentJump;
	private int _currentLine;
	private int _currentSwitch;
	private final Set<Label> _jumpLabels = new HashSet<>();
	private final Map<Label, Integer> _lineLabels = new HashMap<>();
	private final MethodNode _methodNode;
	private final MethodVisitor _methodVisitor;
	private final Map<Label, SwitchHolder> _switchLabels = new HashMap<>();

}
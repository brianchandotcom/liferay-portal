/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat710.model;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link ManyColumnsEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ManyColumnsEntry
 * @generated
 */
public class ManyColumnsEntryWrapper
	implements ManyColumnsEntry, ModelWrapper<ManyColumnsEntry> {

	public ManyColumnsEntryWrapper(ManyColumnsEntry manyColumnsEntry) {
		_manyColumnsEntry = manyColumnsEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return ManyColumnsEntry.class;
	}

	@Override
	public String getModelClassName() {
		return ManyColumnsEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("manyColumnsEntryId", getManyColumnsEntryId());
		attributes.put("column1", getColumn1());
		attributes.put("column2", getColumn2());
		attributes.put("column3", getColumn3());
		attributes.put("column4", getColumn4());
		attributes.put("column5", getColumn5());
		attributes.put("column6", getColumn6());
		attributes.put("column7", getColumn7());
		attributes.put("column8", getColumn8());
		attributes.put("column9", getColumn9());
		attributes.put("column10", getColumn10());
		attributes.put("column11", getColumn11());
		attributes.put("column12", getColumn12());
		attributes.put("column13", getColumn13());
		attributes.put("column14", getColumn14());
		attributes.put("column15", getColumn15());
		attributes.put("column16", getColumn16());
		attributes.put("column17", getColumn17());
		attributes.put("column18", getColumn18());
		attributes.put("column19", getColumn19());
		attributes.put("column20", getColumn20());
		attributes.put("column21", getColumn21());
		attributes.put("column22", getColumn22());
		attributes.put("column23", getColumn23());
		attributes.put("column24", getColumn24());
		attributes.put("column25", getColumn25());
		attributes.put("column26", getColumn26());
		attributes.put("column27", getColumn27());
		attributes.put("column28", getColumn28());
		attributes.put("column29", getColumn29());
		attributes.put("column30", getColumn30());
		attributes.put("column31", getColumn31());
		attributes.put("column32", getColumn32());
		attributes.put("column33", getColumn33());
		attributes.put("column34", getColumn34());
		attributes.put("column35", getColumn35());
		attributes.put("column36", getColumn36());
		attributes.put("column37", getColumn37());
		attributes.put("column38", getColumn38());
		attributes.put("column39", getColumn39());
		attributes.put("column40", getColumn40());
		attributes.put("column41", getColumn41());
		attributes.put("column42", getColumn42());
		attributes.put("column43", getColumn43());
		attributes.put("column44", getColumn44());
		attributes.put("column45", getColumn45());
		attributes.put("column46", getColumn46());
		attributes.put("column47", getColumn47());
		attributes.put("column48", getColumn48());
		attributes.put("column49", getColumn49());
		attributes.put("column50", getColumn50());
		attributes.put("column51", getColumn51());
		attributes.put("column52", getColumn52());
		attributes.put("column53", getColumn53());
		attributes.put("column54", getColumn54());
		attributes.put("column55", getColumn55());
		attributes.put("column56", getColumn56());
		attributes.put("column57", getColumn57());
		attributes.put("column58", getColumn58());
		attributes.put("column59", getColumn59());
		attributes.put("column60", getColumn60());
		attributes.put("column61", getColumn61());
		attributes.put("column62", getColumn62());
		attributes.put("column63", getColumn63());
		attributes.put("column64", getColumn64());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long manyColumnsEntryId = (Long)attributes.get("manyColumnsEntryId");

		if (manyColumnsEntryId != null) {
			setManyColumnsEntryId(manyColumnsEntryId);
		}

		Integer column1 = (Integer)attributes.get("column1");

		if (column1 != null) {
			setColumn1(column1);
		}

		Integer column2 = (Integer)attributes.get("column2");

		if (column2 != null) {
			setColumn2(column2);
		}

		Integer column3 = (Integer)attributes.get("column3");

		if (column3 != null) {
			setColumn3(column3);
		}

		Integer column4 = (Integer)attributes.get("column4");

		if (column4 != null) {
			setColumn4(column4);
		}

		Integer column5 = (Integer)attributes.get("column5");

		if (column5 != null) {
			setColumn5(column5);
		}

		Integer column6 = (Integer)attributes.get("column6");

		if (column6 != null) {
			setColumn6(column6);
		}

		Integer column7 = (Integer)attributes.get("column7");

		if (column7 != null) {
			setColumn7(column7);
		}

		Integer column8 = (Integer)attributes.get("column8");

		if (column8 != null) {
			setColumn8(column8);
		}

		Integer column9 = (Integer)attributes.get("column9");

		if (column9 != null) {
			setColumn9(column9);
		}

		Integer column10 = (Integer)attributes.get("column10");

		if (column10 != null) {
			setColumn10(column10);
		}

		Integer column11 = (Integer)attributes.get("column11");

		if (column11 != null) {
			setColumn11(column11);
		}

		Integer column12 = (Integer)attributes.get("column12");

		if (column12 != null) {
			setColumn12(column12);
		}

		Integer column13 = (Integer)attributes.get("column13");

		if (column13 != null) {
			setColumn13(column13);
		}

		Integer column14 = (Integer)attributes.get("column14");

		if (column14 != null) {
			setColumn14(column14);
		}

		Integer column15 = (Integer)attributes.get("column15");

		if (column15 != null) {
			setColumn15(column15);
		}

		Integer column16 = (Integer)attributes.get("column16");

		if (column16 != null) {
			setColumn16(column16);
		}

		Integer column17 = (Integer)attributes.get("column17");

		if (column17 != null) {
			setColumn17(column17);
		}

		Integer column18 = (Integer)attributes.get("column18");

		if (column18 != null) {
			setColumn18(column18);
		}

		Integer column19 = (Integer)attributes.get("column19");

		if (column19 != null) {
			setColumn19(column19);
		}

		Integer column20 = (Integer)attributes.get("column20");

		if (column20 != null) {
			setColumn20(column20);
		}

		Integer column21 = (Integer)attributes.get("column21");

		if (column21 != null) {
			setColumn21(column21);
		}

		Integer column22 = (Integer)attributes.get("column22");

		if (column22 != null) {
			setColumn22(column22);
		}

		Integer column23 = (Integer)attributes.get("column23");

		if (column23 != null) {
			setColumn23(column23);
		}

		Integer column24 = (Integer)attributes.get("column24");

		if (column24 != null) {
			setColumn24(column24);
		}

		Integer column25 = (Integer)attributes.get("column25");

		if (column25 != null) {
			setColumn25(column25);
		}

		Integer column26 = (Integer)attributes.get("column26");

		if (column26 != null) {
			setColumn26(column26);
		}

		Integer column27 = (Integer)attributes.get("column27");

		if (column27 != null) {
			setColumn27(column27);
		}

		Integer column28 = (Integer)attributes.get("column28");

		if (column28 != null) {
			setColumn28(column28);
		}

		Integer column29 = (Integer)attributes.get("column29");

		if (column29 != null) {
			setColumn29(column29);
		}

		Integer column30 = (Integer)attributes.get("column30");

		if (column30 != null) {
			setColumn30(column30);
		}

		Integer column31 = (Integer)attributes.get("column31");

		if (column31 != null) {
			setColumn31(column31);
		}

		Integer column32 = (Integer)attributes.get("column32");

		if (column32 != null) {
			setColumn32(column32);
		}

		Integer column33 = (Integer)attributes.get("column33");

		if (column33 != null) {
			setColumn33(column33);
		}

		Integer column34 = (Integer)attributes.get("column34");

		if (column34 != null) {
			setColumn34(column34);
		}

		Integer column35 = (Integer)attributes.get("column35");

		if (column35 != null) {
			setColumn35(column35);
		}

		Integer column36 = (Integer)attributes.get("column36");

		if (column36 != null) {
			setColumn36(column36);
		}

		Integer column37 = (Integer)attributes.get("column37");

		if (column37 != null) {
			setColumn37(column37);
		}

		Integer column38 = (Integer)attributes.get("column38");

		if (column38 != null) {
			setColumn38(column38);
		}

		Integer column39 = (Integer)attributes.get("column39");

		if (column39 != null) {
			setColumn39(column39);
		}

		Integer column40 = (Integer)attributes.get("column40");

		if (column40 != null) {
			setColumn40(column40);
		}

		Integer column41 = (Integer)attributes.get("column41");

		if (column41 != null) {
			setColumn41(column41);
		}

		Integer column42 = (Integer)attributes.get("column42");

		if (column42 != null) {
			setColumn42(column42);
		}

		Integer column43 = (Integer)attributes.get("column43");

		if (column43 != null) {
			setColumn43(column43);
		}

		Integer column44 = (Integer)attributes.get("column44");

		if (column44 != null) {
			setColumn44(column44);
		}

		Integer column45 = (Integer)attributes.get("column45");

		if (column45 != null) {
			setColumn45(column45);
		}

		Integer column46 = (Integer)attributes.get("column46");

		if (column46 != null) {
			setColumn46(column46);
		}

		Integer column47 = (Integer)attributes.get("column47");

		if (column47 != null) {
			setColumn47(column47);
		}

		Integer column48 = (Integer)attributes.get("column48");

		if (column48 != null) {
			setColumn48(column48);
		}

		Integer column49 = (Integer)attributes.get("column49");

		if (column49 != null) {
			setColumn49(column49);
		}

		Integer column50 = (Integer)attributes.get("column50");

		if (column50 != null) {
			setColumn50(column50);
		}

		Integer column51 = (Integer)attributes.get("column51");

		if (column51 != null) {
			setColumn51(column51);
		}

		Integer column52 = (Integer)attributes.get("column52");

		if (column52 != null) {
			setColumn52(column52);
		}

		Integer column53 = (Integer)attributes.get("column53");

		if (column53 != null) {
			setColumn53(column53);
		}

		Integer column54 = (Integer)attributes.get("column54");

		if (column54 != null) {
			setColumn54(column54);
		}

		Integer column55 = (Integer)attributes.get("column55");

		if (column55 != null) {
			setColumn55(column55);
		}

		Integer column56 = (Integer)attributes.get("column56");

		if (column56 != null) {
			setColumn56(column56);
		}

		Integer column57 = (Integer)attributes.get("column57");

		if (column57 != null) {
			setColumn57(column57);
		}

		Integer column58 = (Integer)attributes.get("column58");

		if (column58 != null) {
			setColumn58(column58);
		}

		Integer column59 = (Integer)attributes.get("column59");

		if (column59 != null) {
			setColumn59(column59);
		}

		Integer column60 = (Integer)attributes.get("column60");

		if (column60 != null) {
			setColumn60(column60);
		}

		Integer column61 = (Integer)attributes.get("column61");

		if (column61 != null) {
			setColumn61(column61);
		}

		Integer column62 = (Integer)attributes.get("column62");

		if (column62 != null) {
			setColumn62(column62);
		}

		Integer column63 = (Integer)attributes.get("column63");

		if (column63 != null) {
			setColumn63(column63);
		}

		Integer column64 = (Integer)attributes.get("column64");

		if (column64 != null) {
			setColumn64(column64);
		}
	}

	@Override
	public Object clone() {
		return new ManyColumnsEntryWrapper(
			(ManyColumnsEntry)_manyColumnsEntry.clone());
	}

	@Override
	public int compareTo(ManyColumnsEntry manyColumnsEntry) {
		return _manyColumnsEntry.compareTo(manyColumnsEntry);
	}

	/**
	 * Returns the column1 of this many columns entry.
	 *
	 * @return the column1 of this many columns entry
	 */
	@Override
	public int getColumn1() {
		return _manyColumnsEntry.getColumn1();
	}

	/**
	 * Returns the column10 of this many columns entry.
	 *
	 * @return the column10 of this many columns entry
	 */
	@Override
	public int getColumn10() {
		return _manyColumnsEntry.getColumn10();
	}

	/**
	 * Returns the column11 of this many columns entry.
	 *
	 * @return the column11 of this many columns entry
	 */
	@Override
	public int getColumn11() {
		return _manyColumnsEntry.getColumn11();
	}

	/**
	 * Returns the column12 of this many columns entry.
	 *
	 * @return the column12 of this many columns entry
	 */
	@Override
	public int getColumn12() {
		return _manyColumnsEntry.getColumn12();
	}

	/**
	 * Returns the column13 of this many columns entry.
	 *
	 * @return the column13 of this many columns entry
	 */
	@Override
	public int getColumn13() {
		return _manyColumnsEntry.getColumn13();
	}

	/**
	 * Returns the column14 of this many columns entry.
	 *
	 * @return the column14 of this many columns entry
	 */
	@Override
	public int getColumn14() {
		return _manyColumnsEntry.getColumn14();
	}

	/**
	 * Returns the column15 of this many columns entry.
	 *
	 * @return the column15 of this many columns entry
	 */
	@Override
	public int getColumn15() {
		return _manyColumnsEntry.getColumn15();
	}

	/**
	 * Returns the column16 of this many columns entry.
	 *
	 * @return the column16 of this many columns entry
	 */
	@Override
	public int getColumn16() {
		return _manyColumnsEntry.getColumn16();
	}

	/**
	 * Returns the column17 of this many columns entry.
	 *
	 * @return the column17 of this many columns entry
	 */
	@Override
	public int getColumn17() {
		return _manyColumnsEntry.getColumn17();
	}

	/**
	 * Returns the column18 of this many columns entry.
	 *
	 * @return the column18 of this many columns entry
	 */
	@Override
	public int getColumn18() {
		return _manyColumnsEntry.getColumn18();
	}

	/**
	 * Returns the column19 of this many columns entry.
	 *
	 * @return the column19 of this many columns entry
	 */
	@Override
	public int getColumn19() {
		return _manyColumnsEntry.getColumn19();
	}

	/**
	 * Returns the column2 of this many columns entry.
	 *
	 * @return the column2 of this many columns entry
	 */
	@Override
	public int getColumn2() {
		return _manyColumnsEntry.getColumn2();
	}

	/**
	 * Returns the column20 of this many columns entry.
	 *
	 * @return the column20 of this many columns entry
	 */
	@Override
	public int getColumn20() {
		return _manyColumnsEntry.getColumn20();
	}

	/**
	 * Returns the column21 of this many columns entry.
	 *
	 * @return the column21 of this many columns entry
	 */
	@Override
	public int getColumn21() {
		return _manyColumnsEntry.getColumn21();
	}

	/**
	 * Returns the column22 of this many columns entry.
	 *
	 * @return the column22 of this many columns entry
	 */
	@Override
	public int getColumn22() {
		return _manyColumnsEntry.getColumn22();
	}

	/**
	 * Returns the column23 of this many columns entry.
	 *
	 * @return the column23 of this many columns entry
	 */
	@Override
	public int getColumn23() {
		return _manyColumnsEntry.getColumn23();
	}

	/**
	 * Returns the column24 of this many columns entry.
	 *
	 * @return the column24 of this many columns entry
	 */
	@Override
	public int getColumn24() {
		return _manyColumnsEntry.getColumn24();
	}

	/**
	 * Returns the column25 of this many columns entry.
	 *
	 * @return the column25 of this many columns entry
	 */
	@Override
	public int getColumn25() {
		return _manyColumnsEntry.getColumn25();
	}

	/**
	 * Returns the column26 of this many columns entry.
	 *
	 * @return the column26 of this many columns entry
	 */
	@Override
	public int getColumn26() {
		return _manyColumnsEntry.getColumn26();
	}

	/**
	 * Returns the column27 of this many columns entry.
	 *
	 * @return the column27 of this many columns entry
	 */
	@Override
	public int getColumn27() {
		return _manyColumnsEntry.getColumn27();
	}

	/**
	 * Returns the column28 of this many columns entry.
	 *
	 * @return the column28 of this many columns entry
	 */
	@Override
	public int getColumn28() {
		return _manyColumnsEntry.getColumn28();
	}

	/**
	 * Returns the column29 of this many columns entry.
	 *
	 * @return the column29 of this many columns entry
	 */
	@Override
	public int getColumn29() {
		return _manyColumnsEntry.getColumn29();
	}

	/**
	 * Returns the column3 of this many columns entry.
	 *
	 * @return the column3 of this many columns entry
	 */
	@Override
	public int getColumn3() {
		return _manyColumnsEntry.getColumn3();
	}

	/**
	 * Returns the column30 of this many columns entry.
	 *
	 * @return the column30 of this many columns entry
	 */
	@Override
	public int getColumn30() {
		return _manyColumnsEntry.getColumn30();
	}

	/**
	 * Returns the column31 of this many columns entry.
	 *
	 * @return the column31 of this many columns entry
	 */
	@Override
	public int getColumn31() {
		return _manyColumnsEntry.getColumn31();
	}

	/**
	 * Returns the column32 of this many columns entry.
	 *
	 * @return the column32 of this many columns entry
	 */
	@Override
	public int getColumn32() {
		return _manyColumnsEntry.getColumn32();
	}

	/**
	 * Returns the column33 of this many columns entry.
	 *
	 * @return the column33 of this many columns entry
	 */
	@Override
	public int getColumn33() {
		return _manyColumnsEntry.getColumn33();
	}

	/**
	 * Returns the column34 of this many columns entry.
	 *
	 * @return the column34 of this many columns entry
	 */
	@Override
	public int getColumn34() {
		return _manyColumnsEntry.getColumn34();
	}

	/**
	 * Returns the column35 of this many columns entry.
	 *
	 * @return the column35 of this many columns entry
	 */
	@Override
	public int getColumn35() {
		return _manyColumnsEntry.getColumn35();
	}

	/**
	 * Returns the column36 of this many columns entry.
	 *
	 * @return the column36 of this many columns entry
	 */
	@Override
	public int getColumn36() {
		return _manyColumnsEntry.getColumn36();
	}

	/**
	 * Returns the column37 of this many columns entry.
	 *
	 * @return the column37 of this many columns entry
	 */
	@Override
	public int getColumn37() {
		return _manyColumnsEntry.getColumn37();
	}

	/**
	 * Returns the column38 of this many columns entry.
	 *
	 * @return the column38 of this many columns entry
	 */
	@Override
	public int getColumn38() {
		return _manyColumnsEntry.getColumn38();
	}

	/**
	 * Returns the column39 of this many columns entry.
	 *
	 * @return the column39 of this many columns entry
	 */
	@Override
	public int getColumn39() {
		return _manyColumnsEntry.getColumn39();
	}

	/**
	 * Returns the column4 of this many columns entry.
	 *
	 * @return the column4 of this many columns entry
	 */
	@Override
	public int getColumn4() {
		return _manyColumnsEntry.getColumn4();
	}

	/**
	 * Returns the column40 of this many columns entry.
	 *
	 * @return the column40 of this many columns entry
	 */
	@Override
	public int getColumn40() {
		return _manyColumnsEntry.getColumn40();
	}

	/**
	 * Returns the column41 of this many columns entry.
	 *
	 * @return the column41 of this many columns entry
	 */
	@Override
	public int getColumn41() {
		return _manyColumnsEntry.getColumn41();
	}

	/**
	 * Returns the column42 of this many columns entry.
	 *
	 * @return the column42 of this many columns entry
	 */
	@Override
	public int getColumn42() {
		return _manyColumnsEntry.getColumn42();
	}

	/**
	 * Returns the column43 of this many columns entry.
	 *
	 * @return the column43 of this many columns entry
	 */
	@Override
	public int getColumn43() {
		return _manyColumnsEntry.getColumn43();
	}

	/**
	 * Returns the column44 of this many columns entry.
	 *
	 * @return the column44 of this many columns entry
	 */
	@Override
	public int getColumn44() {
		return _manyColumnsEntry.getColumn44();
	}

	/**
	 * Returns the column45 of this many columns entry.
	 *
	 * @return the column45 of this many columns entry
	 */
	@Override
	public int getColumn45() {
		return _manyColumnsEntry.getColumn45();
	}

	/**
	 * Returns the column46 of this many columns entry.
	 *
	 * @return the column46 of this many columns entry
	 */
	@Override
	public int getColumn46() {
		return _manyColumnsEntry.getColumn46();
	}

	/**
	 * Returns the column47 of this many columns entry.
	 *
	 * @return the column47 of this many columns entry
	 */
	@Override
	public int getColumn47() {
		return _manyColumnsEntry.getColumn47();
	}

	/**
	 * Returns the column48 of this many columns entry.
	 *
	 * @return the column48 of this many columns entry
	 */
	@Override
	public int getColumn48() {
		return _manyColumnsEntry.getColumn48();
	}

	/**
	 * Returns the column49 of this many columns entry.
	 *
	 * @return the column49 of this many columns entry
	 */
	@Override
	public int getColumn49() {
		return _manyColumnsEntry.getColumn49();
	}

	/**
	 * Returns the column5 of this many columns entry.
	 *
	 * @return the column5 of this many columns entry
	 */
	@Override
	public int getColumn5() {
		return _manyColumnsEntry.getColumn5();
	}

	/**
	 * Returns the column50 of this many columns entry.
	 *
	 * @return the column50 of this many columns entry
	 */
	@Override
	public int getColumn50() {
		return _manyColumnsEntry.getColumn50();
	}

	/**
	 * Returns the column51 of this many columns entry.
	 *
	 * @return the column51 of this many columns entry
	 */
	@Override
	public int getColumn51() {
		return _manyColumnsEntry.getColumn51();
	}

	/**
	 * Returns the column52 of this many columns entry.
	 *
	 * @return the column52 of this many columns entry
	 */
	@Override
	public int getColumn52() {
		return _manyColumnsEntry.getColumn52();
	}

	/**
	 * Returns the column53 of this many columns entry.
	 *
	 * @return the column53 of this many columns entry
	 */
	@Override
	public int getColumn53() {
		return _manyColumnsEntry.getColumn53();
	}

	/**
	 * Returns the column54 of this many columns entry.
	 *
	 * @return the column54 of this many columns entry
	 */
	@Override
	public int getColumn54() {
		return _manyColumnsEntry.getColumn54();
	}

	/**
	 * Returns the column55 of this many columns entry.
	 *
	 * @return the column55 of this many columns entry
	 */
	@Override
	public int getColumn55() {
		return _manyColumnsEntry.getColumn55();
	}

	/**
	 * Returns the column56 of this many columns entry.
	 *
	 * @return the column56 of this many columns entry
	 */
	@Override
	public int getColumn56() {
		return _manyColumnsEntry.getColumn56();
	}

	/**
	 * Returns the column57 of this many columns entry.
	 *
	 * @return the column57 of this many columns entry
	 */
	@Override
	public int getColumn57() {
		return _manyColumnsEntry.getColumn57();
	}

	/**
	 * Returns the column58 of this many columns entry.
	 *
	 * @return the column58 of this many columns entry
	 */
	@Override
	public int getColumn58() {
		return _manyColumnsEntry.getColumn58();
	}

	/**
	 * Returns the column59 of this many columns entry.
	 *
	 * @return the column59 of this many columns entry
	 */
	@Override
	public int getColumn59() {
		return _manyColumnsEntry.getColumn59();
	}

	/**
	 * Returns the column6 of this many columns entry.
	 *
	 * @return the column6 of this many columns entry
	 */
	@Override
	public int getColumn6() {
		return _manyColumnsEntry.getColumn6();
	}

	/**
	 * Returns the column60 of this many columns entry.
	 *
	 * @return the column60 of this many columns entry
	 */
	@Override
	public int getColumn60() {
		return _manyColumnsEntry.getColumn60();
	}

	/**
	 * Returns the column61 of this many columns entry.
	 *
	 * @return the column61 of this many columns entry
	 */
	@Override
	public int getColumn61() {
		return _manyColumnsEntry.getColumn61();
	}

	/**
	 * Returns the column62 of this many columns entry.
	 *
	 * @return the column62 of this many columns entry
	 */
	@Override
	public int getColumn62() {
		return _manyColumnsEntry.getColumn62();
	}

	/**
	 * Returns the column63 of this many columns entry.
	 *
	 * @return the column63 of this many columns entry
	 */
	@Override
	public int getColumn63() {
		return _manyColumnsEntry.getColumn63();
	}

	/**
	 * Returns the column64 of this many columns entry.
	 *
	 * @return the column64 of this many columns entry
	 */
	@Override
	public int getColumn64() {
		return _manyColumnsEntry.getColumn64();
	}

	/**
	 * Returns the column7 of this many columns entry.
	 *
	 * @return the column7 of this many columns entry
	 */
	@Override
	public int getColumn7() {
		return _manyColumnsEntry.getColumn7();
	}

	/**
	 * Returns the column8 of this many columns entry.
	 *
	 * @return the column8 of this many columns entry
	 */
	@Override
	public int getColumn8() {
		return _manyColumnsEntry.getColumn8();
	}

	/**
	 * Returns the column9 of this many columns entry.
	 *
	 * @return the column9 of this many columns entry
	 */
	@Override
	public int getColumn9() {
		return _manyColumnsEntry.getColumn9();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _manyColumnsEntry.getExpandoBridge();
	}

	/**
	 * Returns the many columns entry ID of this many columns entry.
	 *
	 * @return the many columns entry ID of this many columns entry
	 */
	@Override
	public long getManyColumnsEntryId() {
		return _manyColumnsEntry.getManyColumnsEntryId();
	}

	/**
	 * Returns the primary key of this many columns entry.
	 *
	 * @return the primary key of this many columns entry
	 */
	@Override
	public long getPrimaryKey() {
		return _manyColumnsEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _manyColumnsEntry.getPrimaryKeyObj();
	}

	@Override
	public int hashCode() {
		return _manyColumnsEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _manyColumnsEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _manyColumnsEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _manyColumnsEntry.isNew();
	}

	@Override
	public void persist() {
		_manyColumnsEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_manyColumnsEntry.setCachedModel(cachedModel);
	}

	/**
	 * Sets the column1 of this many columns entry.
	 *
	 * @param column1 the column1 of this many columns entry
	 */
	@Override
	public void setColumn1(int column1) {
		_manyColumnsEntry.setColumn1(column1);
	}

	/**
	 * Sets the column10 of this many columns entry.
	 *
	 * @param column10 the column10 of this many columns entry
	 */
	@Override
	public void setColumn10(int column10) {
		_manyColumnsEntry.setColumn10(column10);
	}

	/**
	 * Sets the column11 of this many columns entry.
	 *
	 * @param column11 the column11 of this many columns entry
	 */
	@Override
	public void setColumn11(int column11) {
		_manyColumnsEntry.setColumn11(column11);
	}

	/**
	 * Sets the column12 of this many columns entry.
	 *
	 * @param column12 the column12 of this many columns entry
	 */
	@Override
	public void setColumn12(int column12) {
		_manyColumnsEntry.setColumn12(column12);
	}

	/**
	 * Sets the column13 of this many columns entry.
	 *
	 * @param column13 the column13 of this many columns entry
	 */
	@Override
	public void setColumn13(int column13) {
		_manyColumnsEntry.setColumn13(column13);
	}

	/**
	 * Sets the column14 of this many columns entry.
	 *
	 * @param column14 the column14 of this many columns entry
	 */
	@Override
	public void setColumn14(int column14) {
		_manyColumnsEntry.setColumn14(column14);
	}

	/**
	 * Sets the column15 of this many columns entry.
	 *
	 * @param column15 the column15 of this many columns entry
	 */
	@Override
	public void setColumn15(int column15) {
		_manyColumnsEntry.setColumn15(column15);
	}

	/**
	 * Sets the column16 of this many columns entry.
	 *
	 * @param column16 the column16 of this many columns entry
	 */
	@Override
	public void setColumn16(int column16) {
		_manyColumnsEntry.setColumn16(column16);
	}

	/**
	 * Sets the column17 of this many columns entry.
	 *
	 * @param column17 the column17 of this many columns entry
	 */
	@Override
	public void setColumn17(int column17) {
		_manyColumnsEntry.setColumn17(column17);
	}

	/**
	 * Sets the column18 of this many columns entry.
	 *
	 * @param column18 the column18 of this many columns entry
	 */
	@Override
	public void setColumn18(int column18) {
		_manyColumnsEntry.setColumn18(column18);
	}

	/**
	 * Sets the column19 of this many columns entry.
	 *
	 * @param column19 the column19 of this many columns entry
	 */
	@Override
	public void setColumn19(int column19) {
		_manyColumnsEntry.setColumn19(column19);
	}

	/**
	 * Sets the column2 of this many columns entry.
	 *
	 * @param column2 the column2 of this many columns entry
	 */
	@Override
	public void setColumn2(int column2) {
		_manyColumnsEntry.setColumn2(column2);
	}

	/**
	 * Sets the column20 of this many columns entry.
	 *
	 * @param column20 the column20 of this many columns entry
	 */
	@Override
	public void setColumn20(int column20) {
		_manyColumnsEntry.setColumn20(column20);
	}

	/**
	 * Sets the column21 of this many columns entry.
	 *
	 * @param column21 the column21 of this many columns entry
	 */
	@Override
	public void setColumn21(int column21) {
		_manyColumnsEntry.setColumn21(column21);
	}

	/**
	 * Sets the column22 of this many columns entry.
	 *
	 * @param column22 the column22 of this many columns entry
	 */
	@Override
	public void setColumn22(int column22) {
		_manyColumnsEntry.setColumn22(column22);
	}

	/**
	 * Sets the column23 of this many columns entry.
	 *
	 * @param column23 the column23 of this many columns entry
	 */
	@Override
	public void setColumn23(int column23) {
		_manyColumnsEntry.setColumn23(column23);
	}

	/**
	 * Sets the column24 of this many columns entry.
	 *
	 * @param column24 the column24 of this many columns entry
	 */
	@Override
	public void setColumn24(int column24) {
		_manyColumnsEntry.setColumn24(column24);
	}

	/**
	 * Sets the column25 of this many columns entry.
	 *
	 * @param column25 the column25 of this many columns entry
	 */
	@Override
	public void setColumn25(int column25) {
		_manyColumnsEntry.setColumn25(column25);
	}

	/**
	 * Sets the column26 of this many columns entry.
	 *
	 * @param column26 the column26 of this many columns entry
	 */
	@Override
	public void setColumn26(int column26) {
		_manyColumnsEntry.setColumn26(column26);
	}

	/**
	 * Sets the column27 of this many columns entry.
	 *
	 * @param column27 the column27 of this many columns entry
	 */
	@Override
	public void setColumn27(int column27) {
		_manyColumnsEntry.setColumn27(column27);
	}

	/**
	 * Sets the column28 of this many columns entry.
	 *
	 * @param column28 the column28 of this many columns entry
	 */
	@Override
	public void setColumn28(int column28) {
		_manyColumnsEntry.setColumn28(column28);
	}

	/**
	 * Sets the column29 of this many columns entry.
	 *
	 * @param column29 the column29 of this many columns entry
	 */
	@Override
	public void setColumn29(int column29) {
		_manyColumnsEntry.setColumn29(column29);
	}

	/**
	 * Sets the column3 of this many columns entry.
	 *
	 * @param column3 the column3 of this many columns entry
	 */
	@Override
	public void setColumn3(int column3) {
		_manyColumnsEntry.setColumn3(column3);
	}

	/**
	 * Sets the column30 of this many columns entry.
	 *
	 * @param column30 the column30 of this many columns entry
	 */
	@Override
	public void setColumn30(int column30) {
		_manyColumnsEntry.setColumn30(column30);
	}

	/**
	 * Sets the column31 of this many columns entry.
	 *
	 * @param column31 the column31 of this many columns entry
	 */
	@Override
	public void setColumn31(int column31) {
		_manyColumnsEntry.setColumn31(column31);
	}

	/**
	 * Sets the column32 of this many columns entry.
	 *
	 * @param column32 the column32 of this many columns entry
	 */
	@Override
	public void setColumn32(int column32) {
		_manyColumnsEntry.setColumn32(column32);
	}

	/**
	 * Sets the column33 of this many columns entry.
	 *
	 * @param column33 the column33 of this many columns entry
	 */
	@Override
	public void setColumn33(int column33) {
		_manyColumnsEntry.setColumn33(column33);
	}

	/**
	 * Sets the column34 of this many columns entry.
	 *
	 * @param column34 the column34 of this many columns entry
	 */
	@Override
	public void setColumn34(int column34) {
		_manyColumnsEntry.setColumn34(column34);
	}

	/**
	 * Sets the column35 of this many columns entry.
	 *
	 * @param column35 the column35 of this many columns entry
	 */
	@Override
	public void setColumn35(int column35) {
		_manyColumnsEntry.setColumn35(column35);
	}

	/**
	 * Sets the column36 of this many columns entry.
	 *
	 * @param column36 the column36 of this many columns entry
	 */
	@Override
	public void setColumn36(int column36) {
		_manyColumnsEntry.setColumn36(column36);
	}

	/**
	 * Sets the column37 of this many columns entry.
	 *
	 * @param column37 the column37 of this many columns entry
	 */
	@Override
	public void setColumn37(int column37) {
		_manyColumnsEntry.setColumn37(column37);
	}

	/**
	 * Sets the column38 of this many columns entry.
	 *
	 * @param column38 the column38 of this many columns entry
	 */
	@Override
	public void setColumn38(int column38) {
		_manyColumnsEntry.setColumn38(column38);
	}

	/**
	 * Sets the column39 of this many columns entry.
	 *
	 * @param column39 the column39 of this many columns entry
	 */
	@Override
	public void setColumn39(int column39) {
		_manyColumnsEntry.setColumn39(column39);
	}

	/**
	 * Sets the column4 of this many columns entry.
	 *
	 * @param column4 the column4 of this many columns entry
	 */
	@Override
	public void setColumn4(int column4) {
		_manyColumnsEntry.setColumn4(column4);
	}

	/**
	 * Sets the column40 of this many columns entry.
	 *
	 * @param column40 the column40 of this many columns entry
	 */
	@Override
	public void setColumn40(int column40) {
		_manyColumnsEntry.setColumn40(column40);
	}

	/**
	 * Sets the column41 of this many columns entry.
	 *
	 * @param column41 the column41 of this many columns entry
	 */
	@Override
	public void setColumn41(int column41) {
		_manyColumnsEntry.setColumn41(column41);
	}

	/**
	 * Sets the column42 of this many columns entry.
	 *
	 * @param column42 the column42 of this many columns entry
	 */
	@Override
	public void setColumn42(int column42) {
		_manyColumnsEntry.setColumn42(column42);
	}

	/**
	 * Sets the column43 of this many columns entry.
	 *
	 * @param column43 the column43 of this many columns entry
	 */
	@Override
	public void setColumn43(int column43) {
		_manyColumnsEntry.setColumn43(column43);
	}

	/**
	 * Sets the column44 of this many columns entry.
	 *
	 * @param column44 the column44 of this many columns entry
	 */
	@Override
	public void setColumn44(int column44) {
		_manyColumnsEntry.setColumn44(column44);
	}

	/**
	 * Sets the column45 of this many columns entry.
	 *
	 * @param column45 the column45 of this many columns entry
	 */
	@Override
	public void setColumn45(int column45) {
		_manyColumnsEntry.setColumn45(column45);
	}

	/**
	 * Sets the column46 of this many columns entry.
	 *
	 * @param column46 the column46 of this many columns entry
	 */
	@Override
	public void setColumn46(int column46) {
		_manyColumnsEntry.setColumn46(column46);
	}

	/**
	 * Sets the column47 of this many columns entry.
	 *
	 * @param column47 the column47 of this many columns entry
	 */
	@Override
	public void setColumn47(int column47) {
		_manyColumnsEntry.setColumn47(column47);
	}

	/**
	 * Sets the column48 of this many columns entry.
	 *
	 * @param column48 the column48 of this many columns entry
	 */
	@Override
	public void setColumn48(int column48) {
		_manyColumnsEntry.setColumn48(column48);
	}

	/**
	 * Sets the column49 of this many columns entry.
	 *
	 * @param column49 the column49 of this many columns entry
	 */
	@Override
	public void setColumn49(int column49) {
		_manyColumnsEntry.setColumn49(column49);
	}

	/**
	 * Sets the column5 of this many columns entry.
	 *
	 * @param column5 the column5 of this many columns entry
	 */
	@Override
	public void setColumn5(int column5) {
		_manyColumnsEntry.setColumn5(column5);
	}

	/**
	 * Sets the column50 of this many columns entry.
	 *
	 * @param column50 the column50 of this many columns entry
	 */
	@Override
	public void setColumn50(int column50) {
		_manyColumnsEntry.setColumn50(column50);
	}

	/**
	 * Sets the column51 of this many columns entry.
	 *
	 * @param column51 the column51 of this many columns entry
	 */
	@Override
	public void setColumn51(int column51) {
		_manyColumnsEntry.setColumn51(column51);
	}

	/**
	 * Sets the column52 of this many columns entry.
	 *
	 * @param column52 the column52 of this many columns entry
	 */
	@Override
	public void setColumn52(int column52) {
		_manyColumnsEntry.setColumn52(column52);
	}

	/**
	 * Sets the column53 of this many columns entry.
	 *
	 * @param column53 the column53 of this many columns entry
	 */
	@Override
	public void setColumn53(int column53) {
		_manyColumnsEntry.setColumn53(column53);
	}

	/**
	 * Sets the column54 of this many columns entry.
	 *
	 * @param column54 the column54 of this many columns entry
	 */
	@Override
	public void setColumn54(int column54) {
		_manyColumnsEntry.setColumn54(column54);
	}

	/**
	 * Sets the column55 of this many columns entry.
	 *
	 * @param column55 the column55 of this many columns entry
	 */
	@Override
	public void setColumn55(int column55) {
		_manyColumnsEntry.setColumn55(column55);
	}

	/**
	 * Sets the column56 of this many columns entry.
	 *
	 * @param column56 the column56 of this many columns entry
	 */
	@Override
	public void setColumn56(int column56) {
		_manyColumnsEntry.setColumn56(column56);
	}

	/**
	 * Sets the column57 of this many columns entry.
	 *
	 * @param column57 the column57 of this many columns entry
	 */
	@Override
	public void setColumn57(int column57) {
		_manyColumnsEntry.setColumn57(column57);
	}

	/**
	 * Sets the column58 of this many columns entry.
	 *
	 * @param column58 the column58 of this many columns entry
	 */
	@Override
	public void setColumn58(int column58) {
		_manyColumnsEntry.setColumn58(column58);
	}

	/**
	 * Sets the column59 of this many columns entry.
	 *
	 * @param column59 the column59 of this many columns entry
	 */
	@Override
	public void setColumn59(int column59) {
		_manyColumnsEntry.setColumn59(column59);
	}

	/**
	 * Sets the column6 of this many columns entry.
	 *
	 * @param column6 the column6 of this many columns entry
	 */
	@Override
	public void setColumn6(int column6) {
		_manyColumnsEntry.setColumn6(column6);
	}

	/**
	 * Sets the column60 of this many columns entry.
	 *
	 * @param column60 the column60 of this many columns entry
	 */
	@Override
	public void setColumn60(int column60) {
		_manyColumnsEntry.setColumn60(column60);
	}

	/**
	 * Sets the column61 of this many columns entry.
	 *
	 * @param column61 the column61 of this many columns entry
	 */
	@Override
	public void setColumn61(int column61) {
		_manyColumnsEntry.setColumn61(column61);
	}

	/**
	 * Sets the column62 of this many columns entry.
	 *
	 * @param column62 the column62 of this many columns entry
	 */
	@Override
	public void setColumn62(int column62) {
		_manyColumnsEntry.setColumn62(column62);
	}

	/**
	 * Sets the column63 of this many columns entry.
	 *
	 * @param column63 the column63 of this many columns entry
	 */
	@Override
	public void setColumn63(int column63) {
		_manyColumnsEntry.setColumn63(column63);
	}

	/**
	 * Sets the column64 of this many columns entry.
	 *
	 * @param column64 the column64 of this many columns entry
	 */
	@Override
	public void setColumn64(int column64) {
		_manyColumnsEntry.setColumn64(column64);
	}

	/**
	 * Sets the column7 of this many columns entry.
	 *
	 * @param column7 the column7 of this many columns entry
	 */
	@Override
	public void setColumn7(int column7) {
		_manyColumnsEntry.setColumn7(column7);
	}

	/**
	 * Sets the column8 of this many columns entry.
	 *
	 * @param column8 the column8 of this many columns entry
	 */
	@Override
	public void setColumn8(int column8) {
		_manyColumnsEntry.setColumn8(column8);
	}

	/**
	 * Sets the column9 of this many columns entry.
	 *
	 * @param column9 the column9 of this many columns entry
	 */
	@Override
	public void setColumn9(int column9) {
		_manyColumnsEntry.setColumn9(column9);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_manyColumnsEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_manyColumnsEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_manyColumnsEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the many columns entry ID of this many columns entry.
	 *
	 * @param manyColumnsEntryId the many columns entry ID of this many columns entry
	 */
	@Override
	public void setManyColumnsEntryId(long manyColumnsEntryId) {
		_manyColumnsEntry.setManyColumnsEntryId(manyColumnsEntryId);
	}

	@Override
	public void setNew(boolean n) {
		_manyColumnsEntry.setNew(n);
	}

	/**
	 * Sets the primary key of this many columns entry.
	 *
	 * @param primaryKey the primary key of this many columns entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_manyColumnsEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_manyColumnsEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<ManyColumnsEntry>
		toCacheModel() {

		return _manyColumnsEntry.toCacheModel();
	}

	@Override
	public ManyColumnsEntry toEscapedModel() {
		return new ManyColumnsEntryWrapper(_manyColumnsEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _manyColumnsEntry.toString();
	}

	@Override
	public ManyColumnsEntry toUnescapedModel() {
		return new ManyColumnsEntryWrapper(
			_manyColumnsEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _manyColumnsEntry.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ManyColumnsEntryWrapper)) {
			return false;
		}

		ManyColumnsEntryWrapper manyColumnsEntryWrapper =
			(ManyColumnsEntryWrapper)object;

		if (Objects.equals(
				_manyColumnsEntry, manyColumnsEntryWrapper._manyColumnsEntry)) {

			return true;
		}

		return false;
	}

	@Override
	public ManyColumnsEntry getWrappedModel() {
		return _manyColumnsEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _manyColumnsEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _manyColumnsEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_manyColumnsEntry.resetOriginalValues();
	}

	private final ManyColumnsEntry _manyColumnsEntry;

}
// LIFERAY-SERVICE-BUILDER-HASH:-596734847
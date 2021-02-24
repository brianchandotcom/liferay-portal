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

package com.liferay.portal.tools.service.builder.test.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class ManyColumnsEntrySoap implements Serializable {

	public static ManyColumnsEntrySoap toSoapModel(ManyColumnsEntry model) {
		ManyColumnsEntrySoap soapModel = new ManyColumnsEntrySoap();

		soapModel.setManyColumnsEntryId(model.getManyColumnsEntryId());
		soapModel.setColumn1(model.getColumn1());
		soapModel.setColumn2(model.getColumn2());
		soapModel.setColumn3(model.getColumn3());
		soapModel.setColumn4(model.getColumn4());
		soapModel.setColumn5(model.getColumn5());
		soapModel.setColumn6(model.getColumn6());
		soapModel.setColumn7(model.getColumn7());
		soapModel.setColumn8(model.getColumn8());
		soapModel.setColumn9(model.getColumn9());
		soapModel.setColumn10(model.getColumn10());
		soapModel.setColumn11(model.getColumn11());
		soapModel.setColumn12(model.getColumn12());
		soapModel.setColumn13(model.getColumn13());
		soapModel.setColumn14(model.getColumn14());
		soapModel.setColumn15(model.getColumn15());
		soapModel.setColumn16(model.getColumn16());
		soapModel.setColumn17(model.getColumn17());
		soapModel.setColumn18(model.getColumn18());
		soapModel.setColumn19(model.getColumn19());
		soapModel.setColumn20(model.getColumn20());
		soapModel.setColumn21(model.getColumn21());
		soapModel.setColumn22(model.getColumn22());
		soapModel.setColumn23(model.getColumn23());
		soapModel.setColumn24(model.getColumn24());
		soapModel.setColumn25(model.getColumn25());
		soapModel.setColumn26(model.getColumn26());
		soapModel.setColumn27(model.getColumn27());
		soapModel.setColumn28(model.getColumn28());
		soapModel.setColumn29(model.getColumn29());
		soapModel.setColumn30(model.getColumn30());
		soapModel.setColumn31(model.getColumn31());
		soapModel.setColumn32(model.getColumn32());
		soapModel.setColumn33(model.getColumn33());
		soapModel.setColumn34(model.getColumn34());
		soapModel.setColumn35(model.getColumn35());
		soapModel.setColumn36(model.getColumn36());
		soapModel.setColumn37(model.getColumn37());
		soapModel.setColumn38(model.getColumn38());
		soapModel.setColumn39(model.getColumn39());
		soapModel.setColumn40(model.getColumn40());
		soapModel.setColumn41(model.getColumn41());
		soapModel.setColumn42(model.getColumn42());
		soapModel.setColumn43(model.getColumn43());
		soapModel.setColumn44(model.getColumn44());
		soapModel.setColumn45(model.getColumn45());
		soapModel.setColumn46(model.getColumn46());
		soapModel.setColumn47(model.getColumn47());
		soapModel.setColumn48(model.getColumn48());
		soapModel.setColumn49(model.getColumn49());
		soapModel.setColumn50(model.getColumn50());
		soapModel.setColumn51(model.getColumn51());
		soapModel.setColumn52(model.getColumn52());
		soapModel.setColumn53(model.getColumn53());
		soapModel.setColumn54(model.getColumn54());
		soapModel.setColumn55(model.getColumn55());
		soapModel.setColumn56(model.getColumn56());
		soapModel.setColumn57(model.getColumn57());
		soapModel.setColumn58(model.getColumn58());
		soapModel.setColumn59(model.getColumn59());
		soapModel.setColumn60(model.getColumn60());
		soapModel.setColumn61(model.getColumn61());
		soapModel.setColumn62(model.getColumn62());
		soapModel.setColumn63(model.getColumn63());
		soapModel.setColumn64(model.getColumn64());

		return soapModel;
	}

	public static ManyColumnsEntrySoap[] toSoapModels(
		ManyColumnsEntry[] models) {

		ManyColumnsEntrySoap[] soapModels =
			new ManyColumnsEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ManyColumnsEntrySoap[][] toSoapModels(
		ManyColumnsEntry[][] models) {

		ManyColumnsEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new ManyColumnsEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new ManyColumnsEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ManyColumnsEntrySoap[] toSoapModels(
		List<ManyColumnsEntry> models) {

		List<ManyColumnsEntrySoap> soapModels =
			new ArrayList<ManyColumnsEntrySoap>(models.size());

		for (ManyColumnsEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ManyColumnsEntrySoap[soapModels.size()]);
	}

	public ManyColumnsEntrySoap() {
	}

	public long getPrimaryKey() {
		return _manyColumnsEntryId;
	}

	public void setPrimaryKey(long pk) {
		setManyColumnsEntryId(pk);
	}

	public long getManyColumnsEntryId() {
		return _manyColumnsEntryId;
	}

	public void setManyColumnsEntryId(long manyColumnsEntryId) {
		_manyColumnsEntryId = manyColumnsEntryId;
	}

	public int getColumn1() {
		return _column1;
	}

	public void setColumn1(int column1) {
		_column1 = column1;
	}

	public int getColumn2() {
		return _column2;
	}

	public void setColumn2(int column2) {
		_column2 = column2;
	}

	public int getColumn3() {
		return _column3;
	}

	public void setColumn3(int column3) {
		_column3 = column3;
	}

	public int getColumn4() {
		return _column4;
	}

	public void setColumn4(int column4) {
		_column4 = column4;
	}

	public int getColumn5() {
		return _column5;
	}

	public void setColumn5(int column5) {
		_column5 = column5;
	}

	public int getColumn6() {
		return _column6;
	}

	public void setColumn6(int column6) {
		_column6 = column6;
	}

	public int getColumn7() {
		return _column7;
	}

	public void setColumn7(int column7) {
		_column7 = column7;
	}

	public int getColumn8() {
		return _column8;
	}

	public void setColumn8(int column8) {
		_column8 = column8;
	}

	public int getColumn9() {
		return _column9;
	}

	public void setColumn9(int column9) {
		_column9 = column9;
	}

	public int getColumn10() {
		return _column10;
	}

	public void setColumn10(int column10) {
		_column10 = column10;
	}

	public int getColumn11() {
		return _column11;
	}

	public void setColumn11(int column11) {
		_column11 = column11;
	}

	public int getColumn12() {
		return _column12;
	}

	public void setColumn12(int column12) {
		_column12 = column12;
	}

	public int getColumn13() {
		return _column13;
	}

	public void setColumn13(int column13) {
		_column13 = column13;
	}

	public int getColumn14() {
		return _column14;
	}

	public void setColumn14(int column14) {
		_column14 = column14;
	}

	public int getColumn15() {
		return _column15;
	}

	public void setColumn15(int column15) {
		_column15 = column15;
	}

	public int getColumn16() {
		return _column16;
	}

	public void setColumn16(int column16) {
		_column16 = column16;
	}

	public int getColumn17() {
		return _column17;
	}

	public void setColumn17(int column17) {
		_column17 = column17;
	}

	public int getColumn18() {
		return _column18;
	}

	public void setColumn18(int column18) {
		_column18 = column18;
	}

	public int getColumn19() {
		return _column19;
	}

	public void setColumn19(int column19) {
		_column19 = column19;
	}

	public int getColumn20() {
		return _column20;
	}

	public void setColumn20(int column20) {
		_column20 = column20;
	}

	public int getColumn21() {
		return _column21;
	}

	public void setColumn21(int column21) {
		_column21 = column21;
	}

	public int getColumn22() {
		return _column22;
	}

	public void setColumn22(int column22) {
		_column22 = column22;
	}

	public int getColumn23() {
		return _column23;
	}

	public void setColumn23(int column23) {
		_column23 = column23;
	}

	public int getColumn24() {
		return _column24;
	}

	public void setColumn24(int column24) {
		_column24 = column24;
	}

	public int getColumn25() {
		return _column25;
	}

	public void setColumn25(int column25) {
		_column25 = column25;
	}

	public int getColumn26() {
		return _column26;
	}

	public void setColumn26(int column26) {
		_column26 = column26;
	}

	public int getColumn27() {
		return _column27;
	}

	public void setColumn27(int column27) {
		_column27 = column27;
	}

	public int getColumn28() {
		return _column28;
	}

	public void setColumn28(int column28) {
		_column28 = column28;
	}

	public int getColumn29() {
		return _column29;
	}

	public void setColumn29(int column29) {
		_column29 = column29;
	}

	public int getColumn30() {
		return _column30;
	}

	public void setColumn30(int column30) {
		_column30 = column30;
	}

	public int getColumn31() {
		return _column31;
	}

	public void setColumn31(int column31) {
		_column31 = column31;
	}

	public int getColumn32() {
		return _column32;
	}

	public void setColumn32(int column32) {
		_column32 = column32;
	}

	public int getColumn33() {
		return _column33;
	}

	public void setColumn33(int column33) {
		_column33 = column33;
	}

	public int getColumn34() {
		return _column34;
	}

	public void setColumn34(int column34) {
		_column34 = column34;
	}

	public int getColumn35() {
		return _column35;
	}

	public void setColumn35(int column35) {
		_column35 = column35;
	}

	public int getColumn36() {
		return _column36;
	}

	public void setColumn36(int column36) {
		_column36 = column36;
	}

	public int getColumn37() {
		return _column37;
	}

	public void setColumn37(int column37) {
		_column37 = column37;
	}

	public int getColumn38() {
		return _column38;
	}

	public void setColumn38(int column38) {
		_column38 = column38;
	}

	public int getColumn39() {
		return _column39;
	}

	public void setColumn39(int column39) {
		_column39 = column39;
	}

	public int getColumn40() {
		return _column40;
	}

	public void setColumn40(int column40) {
		_column40 = column40;
	}

	public int getColumn41() {
		return _column41;
	}

	public void setColumn41(int column41) {
		_column41 = column41;
	}

	public int getColumn42() {
		return _column42;
	}

	public void setColumn42(int column42) {
		_column42 = column42;
	}

	public int getColumn43() {
		return _column43;
	}

	public void setColumn43(int column43) {
		_column43 = column43;
	}

	public int getColumn44() {
		return _column44;
	}

	public void setColumn44(int column44) {
		_column44 = column44;
	}

	public int getColumn45() {
		return _column45;
	}

	public void setColumn45(int column45) {
		_column45 = column45;
	}

	public int getColumn46() {
		return _column46;
	}

	public void setColumn46(int column46) {
		_column46 = column46;
	}

	public int getColumn47() {
		return _column47;
	}

	public void setColumn47(int column47) {
		_column47 = column47;
	}

	public int getColumn48() {
		return _column48;
	}

	public void setColumn48(int column48) {
		_column48 = column48;
	}

	public int getColumn49() {
		return _column49;
	}

	public void setColumn49(int column49) {
		_column49 = column49;
	}

	public int getColumn50() {
		return _column50;
	}

	public void setColumn50(int column50) {
		_column50 = column50;
	}

	public int getColumn51() {
		return _column51;
	}

	public void setColumn51(int column51) {
		_column51 = column51;
	}

	public int getColumn52() {
		return _column52;
	}

	public void setColumn52(int column52) {
		_column52 = column52;
	}

	public int getColumn53() {
		return _column53;
	}

	public void setColumn53(int column53) {
		_column53 = column53;
	}

	public int getColumn54() {
		return _column54;
	}

	public void setColumn54(int column54) {
		_column54 = column54;
	}

	public int getColumn55() {
		return _column55;
	}

	public void setColumn55(int column55) {
		_column55 = column55;
	}

	public int getColumn56() {
		return _column56;
	}

	public void setColumn56(int column56) {
		_column56 = column56;
	}

	public int getColumn57() {
		return _column57;
	}

	public void setColumn57(int column57) {
		_column57 = column57;
	}

	public int getColumn58() {
		return _column58;
	}

	public void setColumn58(int column58) {
		_column58 = column58;
	}

	public int getColumn59() {
		return _column59;
	}

	public void setColumn59(int column59) {
		_column59 = column59;
	}

	public int getColumn60() {
		return _column60;
	}

	public void setColumn60(int column60) {
		_column60 = column60;
	}

	public int getColumn61() {
		return _column61;
	}

	public void setColumn61(int column61) {
		_column61 = column61;
	}

	public int getColumn62() {
		return _column62;
	}

	public void setColumn62(int column62) {
		_column62 = column62;
	}

	public int getColumn63() {
		return _column63;
	}

	public void setColumn63(int column63) {
		_column63 = column63;
	}

	public int getColumn64() {
		return _column64;
	}

	public void setColumn64(int column64) {
		_column64 = column64;
	}

	private long _manyColumnsEntryId;
	private int _column1;
	private int _column2;
	private int _column3;
	private int _column4;
	private int _column5;
	private int _column6;
	private int _column7;
	private int _column8;
	private int _column9;
	private int _column10;
	private int _column11;
	private int _column12;
	private int _column13;
	private int _column14;
	private int _column15;
	private int _column16;
	private int _column17;
	private int _column18;
	private int _column19;
	private int _column20;
	private int _column21;
	private int _column22;
	private int _column23;
	private int _column24;
	private int _column25;
	private int _column26;
	private int _column27;
	private int _column28;
	private int _column29;
	private int _column30;
	private int _column31;
	private int _column32;
	private int _column33;
	private int _column34;
	private int _column35;
	private int _column36;
	private int _column37;
	private int _column38;
	private int _column39;
	private int _column40;
	private int _column41;
	private int _column42;
	private int _column43;
	private int _column44;
	private int _column45;
	private int _column46;
	private int _column47;
	private int _column48;
	private int _column49;
	private int _column50;
	private int _column51;
	private int _column52;
	private int _column53;
	private int _column54;
	private int _column55;
	private int _column56;
	private int _column57;
	private int _column58;
	private int _column59;
	private int _column60;
	private int _column61;
	private int _column62;
	private int _column63;
	private int _column64;

}
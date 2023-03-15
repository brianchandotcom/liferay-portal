@spira_Data_Source @Data_Source @Deletion @team_FARO @priority_4 @blocked
Feature: Assert Unaffected Segments Do Not Appear During Data Source Deletion
	As an Business User, I should not see unaffected Segments during Data Source deletion

	Background:[Setup] Add a CSV Data Source and create a Segment
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I create a file named "AssertAffectedSegments - ${Random.1}.csv" with the following content:
			"""
			email,givenName,familyName,jobTitle,uniqueField${Random.2}
			${Random.2}@gmail.com,first${Random.2},last${Random.2},Farmer,waoSoUnique
			"""
		* I click the "CSV File" button
		* I browse for a file named "AssertAffectedSegments - ${Random.1}.csv"
		* I name the Data Source "AssertAffectedSegments - ${Random.1}.csv"
		* I click the "Next" button
		* I should see "4" fields mapped and "1" fields not mapped
		* I click the "Select Analytics Cloud Field" dropdown
		* I click the "New Field" button
		* I type "uniqueField" into the "New Field Name" input field
		* I click the "Select" dropdown
		* I click the "Text" dropdown option from the dropdown overlay
		* I click the "Create" button
		* I click the "Done" button
		* I should see that "AssertAffectedSegments - ${Random.1}.csv" was successfully uploaded
		* I go to the "Individuals" page
		* I click the "Known Individuals" tab
		* I search for "first${Random.2} last${Random.2}"
		* I should see an item named "first${Random.2} last${Random.2}" in the table
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Dynamic Segment" dropdown option
		* I select "Individual Attributes" from the criterion type dropdown
		* I create a criteria with the following conditions:
			| uniqueField | contains | waosounique |
		* I name the Dynamic segment "AssertAffectedSegments - ${Random.1}" and save it
		* I go to the "Segments" page
		* I search for "AssertAffectedSegments - ${Random.1}"
		* I should see a "Segment" named "AssertAffectedSegments - ${Random.1}" with "1" items
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Dynamic Segment" dropdown option
		* I select "Individual Attributes" from the criterion type dropdown
		* I create a criteria with the following conditions:
			| givenName | contains | alton |
		* I name the Dynamic segment "Unaffected Segment - ${Random.1}" and save it
		* I go to the "Segments" page
		* I search for "Unaffected Segment - ${Random.1}"
		* I should see a "Segment" named "Unaffected Segment - ${Random.1}" with "1" items

	Scenario: Assert Unaffected Segments Don't Appear During Data Source Deletion
		Given I go to the "Data Source" page
		And I click "AssertAffectedSegments - ${Random.1}.csv" in the table
		When I click the "Delete Data Source" button
		Then I should see that "1 Segments" will be affected on the Data Source deletion page
		When I click the affected "Segments" on the Data Source deletion page
		Then I should see "AssertAffectedSegments - ${Random.1}" in a table
		And I should not see a segment named "Unaffected Segment - ${Random.1}" in the table

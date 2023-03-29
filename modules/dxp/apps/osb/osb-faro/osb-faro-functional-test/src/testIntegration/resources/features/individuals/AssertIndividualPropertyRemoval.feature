@spira_Individuals @Individuals @Deletion @team_FARO @priority_5 @blocked
Feature: Individual Properties are Removed with Data Source Deletion
	As an Business User, an Individual's properties should be removed when its source Data Source is deleted

	Background:[Setup] Add a CSV Data Sourced with an Individuals Properties
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I create a file named "AssertIndividualPropertyRemoval A - ${Random.1}.csv" with the following content:
			"""
			email,familyName,givenName,jobTitle
			${Random.2}@gmail.com,last${Random.2},first${Random.2},Farmer
			"""
		* I click the "CSV File" button
		* I browse for a file named "AssertIndividualPropertyRemoval A - ${Random.1}.csv"
		* I name the Data Source "AssertIndividualPropertyRemoval A - ${Random.1}"
		* I click the "Next" button
		* I should see "4" fields mapped and "0" fields not mapped
		* I click the "Done" button
		* I should see that "AssertIndividualPropertyRemoval A - ${Random.1}" was successfully uploaded
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I create a file named "AssertIndividualPropertyRemoval B - ${Random.1}.csv" with the following content:
			"""
			email,familyName,givenName,country
			${Random.2}@gmail.com,last${Random.2},first${Random.2},Australia
			"""
		* I click the "CSV File" button
		* I browse for a file named "AssertIndividualPropertyRemoval B - ${Random.1}.csv"
		* I name the Data Source "AssertIndividualPropertyRemoval B - ${Random.1}"
		* I click the "Next" button
		* I should see "4" fields mapped and "0" fields not mapped
		* I click the "Done" button
		* I should see that "AssertIndividualPropertyRemoval B - ${Random.1}" was successfully uploaded
		* I go to the "Individuals" page
		* I click the "Known Individuals" tab
		* I search for "first${Random.2} last${Random.2}"
		* I should see an item named "first${Random.2} last${Random.2}" in the table

	Scenario: Delete Data Source with an Individual's Property Data
		Given I go to the "Individuals" page
		And I click the "Known Individuals" tab
		And I search for "first${Random.2} last${Random.2}"
		And I click "first${Random.2} last${Random.2}" in the table
		And I click the "Details" tab
		And I should see the following rows in the Contact Details tab
			| email      | ${Random.2}@gmail.com | email      | AssertIndividualPropertyRemoval B - ${Random.1} |
			| givenName  | first${Random.2}      | givenName  | AssertIndividualPropertyRemoval B - ${Random.1} |
			| familyName | last${Random.2}       | familyName | AssertIndividualPropertyRemoval B - ${Random.1} |
			| jobTitle   | Farmer                | jobTitle   | AssertIndividualPropertyRemoval A - ${Random.1} |
			| country    | Australia             | country    | AssertIndividualPropertyRemoval B - ${Random.1} |
		And I go to the "Data Source" page
		And I click "AssertIndividualPropertyRemoval B - ${Random.1}" in the table
		And I click the "Delete Data Source" button
		When I delete the "AssertIndividualPropertyRemoval B - ${Random.1}" Data Source
		And I go to the "Individuals" page
		And I click the "Known Individuals" tab
		And I search for "first${Random.2} last${Random.2}"
		And I click "first${Random.2} last${Random.2}" in the table
		And I click the "Details" tab
		Then I should see the following rows in the Contact Details tab
			| email      | ${Random.2}@gmail.com | email      | AssertIndividualPropertyRemoval A - ${Random.1} |
			| givenName  | first${Random.2}      | givenName  | AssertIndividualPropertyRemoval A - ${Random.1} |
			| familyName | last${Random.2}       | familyName | AssertIndividualPropertyRemoval A - ${Random.1} |
			| jobTitle   | Farmer                | jobTitle   | AssertIndividualPropertyRemoval A - ${Random.1} |
		And I should not see the following rows in the Contact Details tab
			| country    | Australia             | country    | AssertIndividualPropertyRemoval B - ${Random.1} |

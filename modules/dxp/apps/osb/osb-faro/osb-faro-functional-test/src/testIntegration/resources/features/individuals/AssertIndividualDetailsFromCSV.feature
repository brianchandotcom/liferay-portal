@spira_Individuals @Individuals @CSV @team_FARO @priority_5 @blocked
Feature: Assert an Individual's Details from a CSV Data Source
	As a Business User, I should be able to upload a CSV Data Source and assert the details of imported Individuals

	Background: [Setup] Upload CSV Data Source
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I create a file named "AssertIndividualDetailsFromCSV - ${Random.1}.csv" with the following content:
			"""
			email,givenName,familyName,jobTitle
			${Random.2}@gmail.com,first${Random.2},last${Random.2},Farmer
			"""
		* I click the "CSV File" button
		* I browse for a file named "AssertIndividualDetailsFromCSV - ${Random.1}.csv"
		* I name the Data Source "AssertIndividualDetailsFromCSV - ${Random.1}.csv"
		* I click the "Next" button
		* I should see "4" fields mapped and "0" fields not mapped
		* I click the "Done" button
		* I should see that "AssertIndividualDetailsFromCSV - ${Random.1}.csv" was successfully uploaded

	Scenario: Assert Details Tab
		Given I go to the "Individuals" page
		And I click the "Known Individuals" tab
		And I search for "first${Random.2} last${Random.2}"
		And I should see an item named "first${Random.2} last${Random.2}" in the table
		And I click "first${Random.2} last${Random.2}" in the table
		When I click the "Details" tab
		Then I should see the following rows in the Contact Details tab
			| email      | ${Random.2}@gmail.com | email      | AssertIndividualDetailsFromCSV - ${Random.1}.csv |
			| givenName  | first${Random.2}      | givenName  | AssertIndividualDetailsFromCSV - ${Random.1}.csv |
			| familyName | last${Random.2}       | familyName | AssertIndividualDetailsFromCSV - ${Random.1}.csv |
			| jobTitle   | Farmer                | jobTitle   | AssertIndividualDetailsFromCSV - ${Random.1}.csv |
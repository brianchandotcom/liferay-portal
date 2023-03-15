@spira_Individuals @Individuals @Deletion @team_FARO @priority_5 @blocked
Feature: Data Source Deletion Removes Individuals
	As an Business User, Individuals added from a Data Source should be removed when the Data Source is deleted

	Background: [Setup] Upload CSV Data Source
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I create a file named "AssertDataSourceDeletionRemovesIndividuals - ${Random.1}.csv" with the following content:
			"""
			email,givenName,familyName,jobTitle
			${Random.2}@gmail.com,first${Random.2},last${Random.2},Farmer
			"""
		* I click the "CSV File" button
		* I browse for a file named "AssertDataSourceDeletionRemovesIndividuals - ${Random.1}.csv"
		* I name the Data Source "AssertDataSourceDeletionRemovesIndividuals - ${Random.1}"
		* I click the "Next" button
		* I should see "4" fields mapped and "0" fields not mapped
		* I click the "Done" button
		* I should see that "AssertDataSourceDeletionRemovesIndividuals - ${Random.1}" was successfully uploaded

	Scenario: Assert Data Source Deletion Removes Individuals
		Given I go to the "Individuals" page
		And I click the "Known Individuals" tab
		And I search for "first${Random.2} last${Random.2}"
		And I should see an individual named "first${Random.2} last${Random.2}" in the table
		And I go to the "Data Source" page
		And I click "AssertDataSourceDeletionRemovesIndividuals - ${Random.1}" in the table
		When I click the "Delete Data Source" button
		And I delete the "AssertDataSourceDeletionRemovesIndividuals - ${Random.1}" Data Source
		And I go to the "Individuals" page
		And I click the "Known Individuals" tab
		And I search for "first${Random.2} last${Random.2}"
		Then I should not see an individual named "first${Random.2} last${Random.2}" in the table
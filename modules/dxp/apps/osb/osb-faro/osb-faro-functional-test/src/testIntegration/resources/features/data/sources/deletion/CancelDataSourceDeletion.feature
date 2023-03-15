@spira_Data_Source @Data_Source @Deletion @team_FARO @priority_3 @blocked
Feature: Cancel Deleting a Data Source
	As an Business User, I should be able to cancel deleting a Data Source

	Background:[Setup] Add a CSV Data Source with an Individuals
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I create a file named "CancelDataSourceDeletion - ${Random.1}.csv" with the following content:
			"""
			email,givenName,familyName,jobTitle
			${Random.2}@gmail.com,first${Random.2},last${Random.2},Farmer
			"""
		* I click the "CSV File" button
		* I browse for a file named "CancelDataSourceDeletion - ${Random.1}.csv"
		* I name the Data Source "CancelDataSourceDeletion - ${Random.1}"
		* I click the "Next" button
		* I should see "4" fields mapped and "0" fields not mapped
		* I click the "Done" button
		* I should see that "CancelDataSourceDeletion - ${Random.1}" was successfully uploaded

	Scenario: Cancel Deleting a Data Source
		Given I go to the "Data Source" page
		And I click "CancelDataSourceDeletion - ${Random.1}" in the table
		And I click the "Delete Data Source" button
		When I click the "Cancel" button
		And I go to the "Data Source" page
		And I search for "CancelDataSourceDeletion - ${Random.1}"
		Then I should see a data source named "CancelDataSourceDeletion - ${Random.1}" in the table
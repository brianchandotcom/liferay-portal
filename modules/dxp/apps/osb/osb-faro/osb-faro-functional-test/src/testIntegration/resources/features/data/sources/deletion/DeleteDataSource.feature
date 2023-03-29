@spira_Data_Source @Data_Source @Deletion @team_FARO @priority_5 @blocked
Feature: Deleting a Data Source Does Not Delete Any Other Data Sources
	As an Business User, deleting a data source should not delete any other data sources

	Background:[Setup] Add two CSV Data Sources with Individuals
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I create a file named "DeleteDataSource A - ${Random.1}.csv" with the following content:
			"""
			email,givenName,familyName,jobTitle
			A${Random.2}@gmail.com,firstA${Random.2},lastA${Random.2},Farmer
			"""
		* I click the "CSV File" button
		* I browse for a file named "DeleteDataSource A - ${Random.1}.csv"
		* I name the Data Source "DeleteDataSource A - ${Random.1}.csv"
		* I click the "Next" button
		* I should see "4" fields mapped and "0" fields not mapped
		* I click the "Done" button
		* I should see that "DeleteDataSource A - ${Random.1}.csv" was successfully uploaded
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I create a file named "DeleteDataSource B - ${Random.1}.csv" with the following content:
			"""
			email,givenName,familyName,jobTitle
			B${Random.2}@gmail.com,firstB${Random.2},lastB${Random.2},Teacher
			"""
		* I click the "CSV File" button
		* I browse for a file named "DeleteDataSource B - ${Random.1}.csv"
		* I name the Data Source "DeleteDataSource B - ${Random.1}.csv"
		* I click the "Next" button
		* I should see "4" fields mapped and "0" fields not mapped
		* I click the "Done" button
		* I should see that "DeleteDataSource B - ${Random.1}.csv" was successfully uploaded
		* I go to the "Individuals" page
		* I click the "Known Individuals" tab
		* I search for "firstA${Random.2} lastA${Random.2}"
		* I should see an item named "firstA${Random.2} lastA${Random.2}" in the table
		* I search for "firstB${Random.2} lastB${Random.2}"
		* I should see an item named "firstB${Random.2} lastB${Random.2}" in the table
		
	Scenario: Assert Deleting a Data Source Does Not Delete Any Other Data Sources
		Given I go to the "Data Source" page
		And I click "DeleteDataSource A - ${Random.1}.csv" in the table
		When I click the "Delete Data Source" button
		And I delete the "DeleteDataSource A - ${Random.1}.csv" Data Source
		And I go to the "Data Source" page
		And I search for "DeleteDataSource A - ${Random.1}.csv"
		Then I should not see a "CSV" Data Source named "DeleteDataSource A - ${Random.1}.csv"
		When I search for "DeleteDataSource B - ${Random.1}.csv"
		Then I should see a "CSV" Data Source named "DeleteDataSource B - ${Random.1}.csv"
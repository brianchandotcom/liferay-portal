@spira_Pagination @Pagination @Accounts @List @team_FARO @priority_3
Feature: Account individuals list has pagination
	As a Business User, I should be able to paginate to the second page of account individuals list
  
	Background: [Setup] Navigate to Accounts page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page
	  
	Scenario: Paginate to the second page of account individuals
		Given I click "Schneider and Sons" in the table
		And I click the "Individuals" tab
		And I set the pagination delta to "5"
		When I go to page "2" in the table
		Then I should be on page "2" in the table
		And I should see an individual named "Maria Rau" in the table
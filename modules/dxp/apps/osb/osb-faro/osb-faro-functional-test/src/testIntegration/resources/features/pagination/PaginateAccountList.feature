@spira_Pagination @Pagination @Accounts @List @team_FARO @priority_3
Feature: Account list can be paginated
	As a Business User, I should be able to paginate to the second page in Account list

	Background: [Setup] Navigate to Accounts
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page

	Scenario: Paginate to second page of Account list
		Given I set the pagination delta to "5"
		When I go to page "2" in the table
		Then I should be on page "2" in the table
		And I should see an account named "Quigley-Larkin" in the table